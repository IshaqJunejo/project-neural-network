# Project Neural Network

This is a basic implementation of a simple Artificial Neural Network that uses the MNIST Dataset to classify handwritten digits on a 28 by 28 pixels canvas.

### Working

 - The Source Code exists in the `Neural-Network/` directory.
 - `CSVHandler` class is used to read and write data to and from .`csv` files.
 - `Neuron` class is the basic block of our Neural Network.
 - `Layer` class basically stores objects of `Neuron` class. This class is further inherited by `HiddenLayer` and `OutputLayer` classes for having different activation functions.
 - `Network` class is where all the bits and pieces of our Neural Network come together. This is where Training and Testing of the Network takes place.

### Plans

 - [x] Achieving 90+ percent accuracy.
 - [x] Write weights and biases to `.csv` files.
 - [ ] Expanding from `10 Neurons in 1 Hidden Layer` to `16 Neurons in 2 Hidden Layers each`.
 - [ ] Optimizing for Memory Consumption.

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.