from torch import Tensor
from torch.utils.data import DataLoader, Dataset
from torchvision import datasets
from torchvision.transforms import ToTensor


class FMNISTDataset(Dataset):
    def __init__(self, root: str, train=True, transform=None):
        self.dataset = datasets.FashionMNIST(
            root=root, train=train, download=True, transform=transform
        )

    def __getitem__(self, index) -> tuple[Tensor, Tensor]:
        image, label = self.dataset[index]
        return image, label

    def __len__(self) -> int:
        return len(self.dataset)


def get_data_loaders(root="./data/FMNIST", batch_size=32) -> tuple[DataLoader]:
    test_dataset = FMNISTDataset(root=root, train=False, transform=ToTensor())
    train_dataset = FMNISTDataset(root=root, train=True, transform=ToTensor())

    train_loader = DataLoader(
        train_dataset, batch_size=batch_size, shuffle=True
    )
    test_loader = DataLoader(
        test_dataset, batch_size=batch_size, shuffle=False
    )

    return train_loader, test_loader


def append_to_file(filename, data):
    with open(filename, "a") as file:
        file.write(str(data) + "\n")
