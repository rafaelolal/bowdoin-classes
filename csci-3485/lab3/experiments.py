from datetime import datetime
from time import time

from torch import cuda
from torch.backends import mps
from torchsummary import summary

from data import append_to_file, get_data_loaders
from model import build_model, get_parameter_count, test, train

if mps.is_available():
    device = "mps"
elif cuda.is_available():
    device = "cuda"
else:
    device = "cpu"

print(f"Using device: {device}")

train_loader, test_loader = get_data_loaders()


def time_it(f):
    def wrapper(*args, **kwargs):
        start = time()
        result = f(*args, **kwargs)
        return (time() - start, result)

    return wrapper


def cnn_mlp():
    cnn = build_model(
        classes=10,
        shape=(1, 28, 28),
        convoluted=[
            (4, 3),
            (8, 3),
            (16, 3),
            (32, 3),
            (64, 3),
            (128, 3),
        ],
        linear=[32768, 28, 28],
    )

    mlp = build_model(
        classes=10,
        shape=(1, 28, 28),
        convoluted=[],
        linear=[784] + [330] * 8,
    )

    cnn_param_count = get_parameter_count(cnn)
    mlp_param_count = get_parameter_count(mlp)
    cnn_time, cnn_info = time_it(train)(cnn, train_loader, device)
    mlp_time, mlp_info = time_it(train)(mlp, train_loader, device, 10)
    cnn_accuracy = test(cnn, test_loader, device)
    mlp_accuracy = test(mlp, test_loader, device)

    file_name = "cnn_mlp.txt"
    append_to_file(file_name, datetime.now())
    append_to_file(file_name, f"cnn param count: {cnn_param_count}")
    append_to_file(file_name, f"cnn time: {cnn_time}")
    append_to_file(file_name, f"cnn info: {cnn_info}")
    append_to_file(file_name, f"cnn accuracy: {cnn_accuracy}")
    append_to_file(file_name, f"mlp param count: {mlp_param_count}")
    append_to_file(file_name, f"mlp time: {mlp_time}")
    append_to_file(file_name, f"mlp info: {mlp_info}")
    append_to_file(file_name, f"mlp accuracy: {mlp_accuracy}")


def kernel_size():
    sizes = [2]  # , 3, 5, 7, 9]
    models = []
    for size in sizes:
        model = build_model(
            classes=10,
            shape=(1, 28, 28),
            convoluted=[
                (32, size),
                (64, size),
                (128, size),
            ],
            linear=[128 * (28 - 3 * (size - 1)) ** 2, 28, 28],
        )
        models.append(model)

    parameter_counts = []
    for model in models:
        parameter_count = get_parameter_count(model)
        parameter_counts.append(parameter_count)

    training_times = []
    epochs = []
    for model in models:
        training_time, info = time_it(train)(model, train_loader, device)
        training_times.append(training_time)
        epochs.append(info["epochs"])

    accuracies = []
    for model in models:
        accuracy = test(model, test_loader, device)
        accuracies.append(accuracy)

    file_name = "kernel_size.txt"
    append_to_file(file_name, datetime.now())
    append_to_file(file_name, f"parameter counts: {parameter_counts}")
    append_to_file(file_name, f"training times: {training_times}")
    append_to_file(file_name, f"epochs: {epochs}")
    append_to_file(file_name, f"accuracies: {accuracies}")


def filter_count():
    filter_counts = [5, 10, 15, 20, 25]
    models = []
    for count in filter_counts:
        model = build_model(
            classes=10,
            shape=(1, 28, 28),
            convoluted=[
                (count, 3),
                (count, 3),
                (count, 3),
                (count, 3),
                (count, 3),
                (count, 3),
            ],
            linear=[count * (28 - 6 * (3 - 1)) ** 2, 28, 28],
        )
        models.append(model)

    parameter_counts = []
    for model in models:
        parameter_count = get_parameter_count(model)
        parameter_counts.append(parameter_count)

    training_times = []
    epochs = []
    for model in models:
        training_time, info = time_it(train)(model, train_loader, device)
        training_times.append(training_time)
        epochs.append(info["epochs"])

    accuracies = []
    for model in models:
        accuracy = test(model, test_loader, device)
        accuracies.append(accuracy)

    file_name = "filter_count.txt"
    append_to_file(file_name, datetime.now())
    append_to_file(file_name, f"filter counts: {filter_counts}")
    append_to_file(file_name, f"parameter counts: {parameter_counts}")
    append_to_file(file_name, f"training times: {training_times}")
    append_to_file(file_name, f"epochs: {epochs}")
    append_to_file(file_name, f"accuracies: {accuracies}")
