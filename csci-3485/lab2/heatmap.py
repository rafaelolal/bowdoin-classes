import numpy as np
import matplotlib.pyplot as plt

# Create data
x = np.linspace(0, 100, 101)
y = np.linspace(0, 100, 101)
X, Y = np.meshgrid(x, y)

# Define z as a function of x and y (example function)
Z = np.sin(X/10) * np.cos(Y/10)

# Create heatmap
plt.figure(figsize=(10, 8))
plt.imshow(Z, extent=[0, 100, 0, 100], origin='lower', cmap='viridis')
plt.colorbar(label='Z value')

plt.title('Heatmap of Z values for X and Y ranging from 0 to 100')
plt.xlabel('X')
plt.ylabel('Y')

plt.show()