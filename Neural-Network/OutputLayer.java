class OutputLayer extends Layer {
    // Constructor
    public OutputLayer(int numOfNeurons, int numOfInputsPerNeuron) {
        super(numOfNeurons, numOfInputsPerNeuron);
    }

    // Activation Function
    @Override
    public double[] activation(double[] weightedSums) {
        // Softmax Activation Function
        // Sum of Exponents
        double sum = sumOfExponents(weightedSums);
        // Ratio of Exponents at index i to the sum of exponents
        for (int i = 0; i < weightedSums.length; i++) {
            this.outputs[i] = Math.exp(weightedSums[i]) / sum;
        }
        // Return the outputs
        return this.outputs;
    }

    private double sumOfExponents(double[] weightedSums) {
        double sum = 0;
        for (int i = 0; i < weightedSums.length; i++) {
            sum += Math.exp(weightedSums[i]);
        }
        return sum;
    }
}