import torch.nn as nn
from torch import no_grad
from torch.optim import Adam
from torch.utils.data import DataLoader


def build_model(
    classes: int,
    shape: tuple[int],
    convoluted: list[tuple[int]],
    linear: list[int],
    batch_norm: bool = False,
    dropout: float = 0.0,
) -> nn.Sequential:
    if batch_norm and dropout:
        raise ValueError(
            "batch_norm and dropout cannot both be True for this lab"
        )

    layers = []

    # first layer
    if convoluted:
        layers.append(nn.Conv2d(shape[0], convoluted[0][0], convoluted[0][1]))
        if batch_norm:
            layers.append(nn.BatchNorm2d(convoluted[0][0]))
        layers.append(nn.ReLU())
        if dropout:
            layers.append(nn.Dropout(dropout))

    # convolutional layers
    for i in range(1, len(convoluted)):
        layers.append(
            nn.Conv2d(convoluted[i - 1][0], convoluted[i][0], convoluted[i][1])
        )
        if batch_norm:
            layers.append(nn.BatchNorm2d(convoluted[i][0]))
        layers.append(nn.ReLU())
        if dropout:
            layers.append(nn.Dropout(dropout))

    layers.append(nn.Flatten())

    # linear layers
    for i in range(len(linear) - 1):
        layers.append(nn.Linear(linear[i], linear[i + 1]))
        if batch_norm:
            layers.append(nn.BatchNorm1d(linear[i + 1]))
        layers.append(nn.ReLU())
        if dropout:
            layers.append(nn.Dropout(dropout))

    # last layer
    last_layer = -2
    if batch_norm or dropout:
        last_layer -= 1

    layers.append(nn.Linear(layers[last_layer].out_features, classes))

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
