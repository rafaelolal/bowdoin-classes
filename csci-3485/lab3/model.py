import torch.nn as nn
from torch import no_grad
from torch.optim import Adam
from torch.utils.data import DataLoader


def build_model(
    classes: int,
    shape: tuple[int],
    convoluted: list[tuple[int]],
    linear: list[int],
) -> nn.Sequential:
    layers = []

    # first layer
    if convoluted:
        layers.append(nn.Conv2d(shape[0], convoluted[0][0], convoluted[0][1]))
        layers.append(nn.ReLU())

    for i in range(1, len(convoluted)):
        layers.append(
            nn.Conv2d(convoluted[i - 1][0], convoluted[i][0], convoluted[i][1])
        )
        layers.append(nn.ReLU())

    layers.append(nn.Flatten())

    for i in range(len(linear) - 1):
        layers.append(nn.Linear(linear[i], linear[i + 1]))
        layers.append(nn.ReLU())

    # last layer
    layers.append(nn.Linear(layers[-2].out_features, classes))

    return nn.Sequential(*layers)


def get_parameter_count(model: nn.Sequential) -> int:
    return sum(p.numel() for p in model.parameters())


def train(
    model: nn.Sequential,
    data_loader: DataLoader,
    device: str,
    max_epochs=None,
    lr=1e-3,
    min_delta=1e-2,
):
    model.to(device)
    model.train()

    optimizer = Adam(model.parameters(), lr=lr)
    loss_fn = nn.CrossEntropyLoss()

    best_loss = float("inf")
    epochs = 0
    while max_epochs is None or epochs < max_epochs:
        epoch_loss = 0
        for x, y in data_loader:
            x, y = x.to(device), y.to(device)

            optimizer.zero_grad()
            outputs = model(x)
            loss = loss_fn(outputs, y)
            loss.backward()
            optimizer.step()

            epoch_loss += loss.item()

        epochs += 1
        avg_loss = epoch_loss / len(data_loader)

        if abs(best_loss - avg_loss) < min_delta:
            break

        best_loss = min(best_loss, avg_loss)

    return {"epochs": epochs}


@no_grad()
def test(model: nn.Sequential, data_loader: DataLoader, device: str) -> float:
    model.to(device)
    model.eval()

    correct = 0
    for x, y in data_loader:
        x, y = x.to(device), y.to(device)
        predicted = model(x)
        correct += (predicted.argmax(dim=1) == y).sum().item()

    return correct / len(data_loader.dataset)
