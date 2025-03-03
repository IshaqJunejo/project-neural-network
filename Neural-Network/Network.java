class Network {
    // One Hot Encoding
    static double[] oneHot(int label) {
        double[] oneHot = new double[10];
        for (int i = 0; i < oneHot.length; i++) {
            oneHot[i] = 0;
        }
        oneHot[label] = 1;
        return oneHot;
    }

    // Loss Function
    static double crossEntropyLoss (double[] predicted, double[] actual) {
        // Clipping the predicted values to avoid log(0)
        double[] predictionClipped = new double[predicted.length];
        for (int i = 0; i < predicted.length; i++) {
            predictionClipped[i] = Math.max(1e-15, Math.min(predicted[i], 1 - 1e-15));
        }

        // Computing the Loss
        double loss = 0;
        for (int i = 0; i < predictionClipped.length; i++) {
            loss -= actual[i] * Math.log(predictionClipped[i]);
        }
        return loss;
    }

    // Difference of two arrays
    static double[] differenceOf (double[] array1, double[] array2) {
        if (array1.length != array2.length) {
            // return null;
        }
        double[] difference = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            difference[i] = array1[i] - array2[i];
        }
        return difference;
    }

    // Dot Product (bypassing the transpose)
    static double dotProduct (double[] array1, double[] array2) {
        if (array1.length != array2.length) {
            // return null;
        }
        double sum = 0;
        for (int i = 0; i < array1.length; i++) {
            sum += array1[i] * array2[i];
        }
        return sum;
    }

    // Derivative of ReLU
    static double ReLUDerivative (double x) {
        if (x > 0) {
            return 1;
        }
        return 0;
    }

    // Loss Function
    static void Loss (double[] predicted, double[] actual) {
        double loss = crossEntropyLoss(predicted, actual);
        System.out.println("Loss: " + loss);
    }

    // Find index of the max value in an array
    static int indexOfMax(double[] array) {
        int index = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] >= array[index]) {
                index = i;
            }
        }

        return index;
    }

    // Forward Propagation
    static void forwardPropagation (HiddenLayer layer1, OutputLayer layer2, double[] input) {
        double[] outputs1 = layer1.forward(input);
        double[] outputs2 = layer2.forward(outputs1);
    }

    // Back Propagation
    static void backPropagation (HiddenLayer layer1, OutputLayer layer2, double[] input, int label) {
        // One Hot Encoding
        double[] oneHotLabel = oneHot(label);

        // Gradient with respect to the output of the output layer
        double[] dOutput2 = differenceOf(layer2.getOutputs(), oneHotLabel);

        // Gradient with respect to the output layer weights
        double[][] dWeights2 = new double[layer2.getWeights().length][layer2.getWeights()[0].length];
        for (int i = 0; i < dWeights2.length; i++) {
            for (int j = 0; j < dWeights2[i].length; j++) {
                dWeights2[i][j] = dOutput2[i] * layer1.getOutputs()[j];
            }
        }

        // Gradient with respect to the output layer biases
        double[] dBiases2 = dOutput2;

        // Gradient with respect to the output of hidden layer
        double[] dHidden = new double[layer1.getWeights().length];
        for (int i = 0; i < dHidden.length; i++) {
            dHidden[i] = dotProduct(dOutput2, layer2.getWeights()[i]);
        }

        // Gradient with respect to the hidden layer weights
        double[][] dWeights1 = new double[layer1.getWeights().length][layer1.getWeights()[0].length];
        for (int i = 0; i < dWeights1.length; i++) {
            for (int j = 0; j < dWeights1[i].length; j++) {
                dWeights1[i][j] = dHidden[i] * ReLUDerivative(layer1.getOutputs()[i]) * input[j];
            }
        }

        // Gradient with respect to the hidden layer biases
        double[] dBiases1 = new double[layer1.getBiases().length];
        for (int i = 0; i < dBiases1.length; i++) {
            dBiases1[i] = dHidden[i] * ReLUDerivative(layer1.getOutputs()[i]);
        }

        // Updating Weights and Biases
        double learningRate = 0.001;
        layer2.updateWeightsAndBiases(dWeights2, dBiases2, learningRate);
        layer1.updateWeightsAndBiases(dWeights1, dBiases1, learningRate);
    }

    public static void main (String[] args) {
        // Reading the data from the csv files
        int[][] trainData = CSVHandler.readDataFrom("mnist_train.csv", 60000, (28 * 28 + 1));
        int[][] testData = CSVHandler.readDataFrom("mnist_test.csv", 10000, (28 * 28 + 1));

        double[][] trainInput = new double[60000][28 * 28];
        int[] trainLabel = new int[60000];

        double[][] testInput = new double[10000][28 * 28];
        int[] testLabel = new int[10000];

        for (int i = 0; i < trainData.length; i++) {
            // Splitting training data into input and label
            for (int j = 1; j < trainData[i].length; j++) {
                trainInput[i][j - 1] = (double)trainData[i][j] / 255.0;
            }
            trainLabel[i] = trainData[i][0];

            // Splitting testing data into input and label
            if (i < testData.length) {
                for (int j = 1; j < testData[i].length; j++) {
                    testInput[i][j - 1] = (double)testData[i][j] / 255.0;
                }
                testLabel[i] = testData[i][0];
            }
        }

        System.out.println("Data Loaded");

        // Creating a Neural Network
        HiddenLayer layer1 = new HiddenLayer(10, (28 * 28));
        OutputLayer layer2 = new OutputLayer(10, 10);

        System.out.println("Network Layers Assembled");

        // Training the Neural Network
        int EPOCHS = 1000;
        for (int i = 0; i < EPOCHS; i++) {
            int correctPredictions = 0;
            int totalPredictions = 0;
            for (int j = 0; j < trainInput.length; j++) {
                forwardPropagation(layer1, layer2, trainInput[j]);
                backPropagation(layer1, layer2, trainInput[j], trainLabel[j]);

                // Computing Accuracy
                totalPredictions++;
                double[] prediction = layer2.getOutputs();
                if (indexOfMax(prediction) == trainLabel[j]) {
                    correctPredictions++;
                }
            }

            // Displaying the Accuracy of the Network
            System.out.println("Epoch number: " + i);
            System.out.println("Correctly Predicted: " + correctPredictions + " out of " + totalPredictions);
            System.out.println("Accuracy: " + ((double)correctPredictions / (double)totalPredictions * 100.0) + "%");
            System.out.println();
        }
    }
}