import matplotlib.pyplot as plt
import numpy as np
from sklearn.datasets import make_blobs


def activation(n: float) -> int:
    """Returns 1 if n is positive or zero, -1
    otherwise.
    """

    if n >= 0:
        return 1
    else:
        return -1


def perceptron(
    X: np.ndarray, Y: np.ndarray
) -> list[float]:
    """Trains a perceptron on the given data and returns
    the weights.
    """

    X = np.hstack((np.ones((N, 1)), X))
    w = [0 for _ in range(len(X[0]))]

    while True:
        for i in range(len(X)):
            inner_product = np.inner(w, X[i])

            y_hat = activation(inner_product)
            if Y[i] > y_hat:
                w = np.add(w, X[i])
            elif Y[i] < y_hat:
                w = np.subtract(w, X[i])
            else:
                continue

            break

        else:
            break

    return w


def get_points_from_weights(
    w: list[float],
) -> tuple[np.ndarray, np.ndarray]:
    """Creates x_1 and x_2 coordinates given weights that
    describe a decision boundary from a linear classification.
    """

    x_1 = np.linspace(-10, 10, 1000)
    x_2 = -(w[0] + w[1] * x_1) / w[2]

    return x_1, x_2


N = 500
D = 2
X, Y = make_blobs(
    n_samples=N, centers=2, n_features=D, random_state=1
)
Y = 2 * (Y - 0.5)

below = X[Y == -1]
above = X[Y == 1]
plt.scatter(below[:, 0], below[:, 1], c="r", s=10)
plt.scatter(above[:, 0], above[:, 1], c="g", s=10)

w = perceptron(X, Y)
x_1, x_2 = get_points_from_weights(w)
plt.plot(x_1, x_2)

plt.show()
