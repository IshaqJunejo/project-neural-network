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
            return null;
        }
        double[] difference = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            difference[i] = array1[i] - array2[i];
        }
        return difference;
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
        // Computing the Loss
        double loss = crossEntropyLoss(layer2.getOutputs(), oneHotLabel);
        // Gradient with respect to the output of the output layer
        double[] dOutput2 = differenceOf(layer2.getOutputs(), oneHotLabel);
        // Gradient with respect to the output layer weights
        // Gradient with respect to the output layer biases
        // Gradient with respect to the output of hidden layer
        // Gradient with respect to the hidden layer biases
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

        // Creating a Neural Network
        HiddenLayer layer1 = new HiddenLayer(10, (28 * 28));
        OutputLayer layer2 = new OutputLayer(10, 10);

        // Forward Propagation
        double[] outputs1 = layer1.forward(trainInput[0]);
        double[] output2 = layer2.forward(outputs1);

        for (int i = 0; i < output2.length; i++) {
            System.out.println(output2[i]);
        }
    }
}