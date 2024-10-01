"""
Manages the data for the experiments.
"""

from torch import Tensor
from torch.utils.data import DataLoader, Dataset
from torchvision import datasets
from torchvision.transforms import ToTensor


class FMNISTDataset(Dataset):
    def __init__(self, root: str, train: bool = True, transform=None) -> None:
        self.dataset = datasets.FashionMNIST(
            root=root, train=train, download=True, transform=transform
        )

    def __getitem__(self, index) -> tuple[Tensor, Tensor]:
        image, label = self.dataset[index]
        return image, label

    def __len__(self) -> int:
        return len(self.dataset)


def get_data_loaders(
    root: str = "./data/FMNIST", batch_size: int = 32
) -> tuple[DataLoader]:
    # Using ToTensor instead of reshaping as we did in class to better leverage the GPU
    test_dataset = FMNISTDataset(root=root, train=False, transform=ToTensor())
    train_dataset = FMNISTDataset(root=root, train=True, transform=ToTensor())

    train_loader = DataLoader(
        train_dataset, batch_size=batch_size, shuffle=True
    )
    test_loader = DataLoader(
        test_dataset, batch_size=batch_size, shuffle=False
    )

    return train_loader, test_loader


def append_to_file(filename: str, data: any) -> None:
    with open(filename, "a") as file:
        file.write(str(data) + "\n")
