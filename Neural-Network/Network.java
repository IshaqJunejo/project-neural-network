class Network {
    public static void main (String[] args) {
        // Reading the data from the file
        int[][] data = CSVHandler.readDataFrom("mnist_test.csv", 10000, (28 * 28 + 1));

        // Creating a Neural Network
        HiddenLayer layer1 = new HiddenLayer(10, (28 * 28));
        OutputLayer layer2 = new OutputLayer(10, 10);

        // selecting a random data-point
        int index = (int)(Math.random() * 10000);
        // Seperating the input and label from the randomly selected data-point
        double[] input = new double[28 * 28];
        for (int i = 1; i < (28 * 28); i++) {
            input[i - 1] = (double)data[index][i] / 255.0;
        }

        layer1.forward(input);
        layer2.forward(layer1.getOutputs());
        //double[] outputs = layer2.getOutputs();

        for (int i = 0; i < layer2.getOutputs().length; i++) {
            System.out.println("Probablity of being a " + i + ": " + Math.round(layer2.getOutputs()[i] * 1000.0) / 1000.0);
        }
        System.out.println("Label: " + data[index][0]);
    }
}