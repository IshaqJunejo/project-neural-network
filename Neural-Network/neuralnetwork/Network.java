package neuralnetwork;

import java.util.Scanner;

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

    // Transpose
    static double[][] transpose (double[][] array) {
        double[][] transposed = new double[array[0].length][array.length];
        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[i].length; j++) {
                transposed[j][i] = array[i][j];
            }
        }

        return transposed;
    }

    // Dot Product
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
    static void forwardPropagation (HiddenLayer layer1, HiddenLayer layer2, OutputLayer layer3, double[] input) {
        double[] outputs1 = layer1.forward(input);
        double[] outputs2 = layer2.forward(outputs1);
        double[] outputs3 = layer3.forward(outputs2);
    }

    // Back Propagation
    static void backPropagation (HiddenLayer layer1, HiddenLayer layer2, OutputLayer layer3, double[] input, int label) {
        // One Hot Encoding
        double[] oneHotLabel = oneHot(label);

        // Gradient with respect to the output of the output layer
        double[] dOutput3 = differenceOf(layer3.getOutputs(), oneHotLabel);

        // Gradient with respect to the output layer weights
        double[][] dWeights3 = new double[layer3.getWeights().length][layer3.getWeights()[0].length];
        for (int i = 0; i < dWeights3.length; i++) {
            for (int j = 0; j < dWeights3[i].length; j++) {
                dWeights3[i][j] = dOutput3[i] * layer2.getOutputs()[j];
            }
        }

        // Gradient with respect to the output layer biases
        double[] dBiases3 = dOutput3;

        // Gradient with respect to the output of 2nd hidden layer
        double[][] transposeOfWeights3 = transpose(layer3.getWeights());
        double[] dHidden2 = new double[layer2.getWeights().length];
        for (int i = 0; i < dHidden2.length; i++) {
            dHidden2[i] = dotProduct(dOutput3, transposeOfWeights3[i]);
        }

        // Gradient with respect to the 2nd hidden layer weights
        double[][] dWeights2 = new double[layer2.getWeights().length][layer2.getWeights()[0].length];
        for (int i = 0; i < dWeights2.length; i++) {
            for (int j = 0; j < dWeights2[i].length; j++) {
                dWeights2[i][j] = dHidden2[i] * ReLUDerivative(layer2.getOutputs()[i]) * layer1.getOutputs()[j];
            }
        }

        // Gradient with respect to the 2nd hidden layer biases
        double[] dBiases2 = new double[layer2.getBiases().length];
        for (int i = 0; i < dBiases2.length; i++) {
            dBiases2[i] = dHidden2[i] * ReLUDerivative(layer2.getOutputs()[i]);
        }

        // Gradient with respect to the output of 1st hidden layer
        double[][] transposeOfWeights2 = transpose(layer2.getWeights());
        double[] dHidden1 = new double[layer1.getWeights().length];
        for (int i = 0; i < dHidden1.length; i++) {
            dHidden1[i] = dotProduct(dHidden2, transposeOfWeights2[i]);
        }

        // Gradient with respect to the 1st hidden layer weights
        double[][] dWeights1 = new double[layer1.getWeights().length][layer1.getWeights()[0].length];
        for (int i = 0; i < dWeights1.length; i++) {
            for (int j = 0; j < dWeights1[i].length; j++) {
                dWeights1[i][j] = dHidden1[i] * ReLUDerivative(layer1.getOutputs()[i]) * input[j];
            }
        }

        // Gradient with respect to the 1st hidden layer biases
        double[] dBiases1 = new double[layer1.getBiases().length];
        for (int i = 0; i < dBiases1.length; i++) {
            dBiases1[i] = dHidden1[i] * ReLUDerivative(layer1.getOutputs()[i]);
        }

        // Updating Weights and Biases
        double learningRate = 0.001;
        layer3.updateWeightsAndBiases(dWeights3, dBiases3, learningRate);
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
        HiddenLayer layer1 = new HiddenLayer((28 * 28), 16);
        HiddenLayer layer2 = new HiddenLayer(16, 16);
        OutputLayer layer3 = new OutputLayer(16, 10);

        System.out.println("Network Layers Assembled");

        // Training the Neural Network
        System.out.println("Training the Network");
        System.out.println();

        int EPOCHS = 100;
        for (int i = 0; i < EPOCHS; i++) {
            int correctPredictions = 0;
            int totalPredictions = 0;
            for (int j = 0; j < trainInput.length; j++) {
                forwardPropagation(layer1, layer2, layer3, trainInput[j]);
                backPropagation(layer1, layer2, layer3, trainInput[j], trainLabel[j]);

                // Computing Accuracy
                totalPredictions++;
                double[] prediction = layer3.getOutputs();
                if (indexOfMax(prediction) == trainLabel[j]) {
                    correctPredictions++;
                }
            }

            // Displaying the Accuracy of the Network
            System.out.println("Epoch number: " + (i + 1));
            System.out.println("Loss: " + crossEntropyLoss(layer3.getOutputs(), oneHot(trainLabel[trainLabel.length - 1])));
            System.out.println("Correctly Predicted: " + correctPredictions + " out of " + totalPredictions);
            System.out.println("Accuracy: " + ((double)correctPredictions / (double)totalPredictions * 100.0) + "%");
            System.out.println();
        }

        // Testing the Neural Network
        System.out.println("Testing the Network");

        int correctPredictions = 0;
        int totalPredictions = 0;
        for (int i = 0; i < testInput.length; i++) {
            forwardPropagation(layer1, layer2, layer3, testInput[i]);

            // Computing Accuracy
            totalPredictions++;
            double[] prediction = layer3.getOutputs();
            if (indexOfMax(prediction) == testLabel[i]) {
                correctPredictions++;
            }
        }

        System.out.println("Correctly Prediction: " + correctPredictions + " out of " + totalPredictions);
        System.out.println("Accuracy: " + ((double)correctPredictions / (double)totalPredictions * 100.0) + "%");
        System.out.println();

        // Saving the weights and biases
        int asker = 0;
        Scanner input = new Scanner(System.in);
        System.out.println("Do you want to save the weights and biases? (1 for Yes, 0 for No)");
        asker = input.nextInt();

        if (asker == 1) {
            CSVHandler.writeDataTo("weights1.csv", layer1.getWeights());
            CSVHandler.writeDataTo("biases1.csv", layer1.getBiases());
            CSVHandler.writeDataTo("weights2.csv", layer2.getWeights());
            CSVHandler.writeDataTo("biases2.csv", layer2.getBiases());
            CSVHandler.writeDataTo("weights3.csv", layer3.getWeights());
            CSVHandler.writeDataTo("biases3.csv", layer3.getBiases());
        }
    }
}