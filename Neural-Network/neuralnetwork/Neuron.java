package neuralnetwork;

class Neuron {
    // Neuron Attributes
    private double[] weights;
    private double bias;
    private double output;
    private double weightedSum;

    // Constructor
    public Neuron (int numOfInputs) {
        this.weights = new double[numOfInputs];
        // Randomly initialize weights and bias (range: -1 to 1)
        for (int i = 0; i < numOfInputs; i++) {
            this.weights[i] = Math.random() * 2 -1;
        }
        this.bias = Math.random() * 2 - 1;

        // Output is not initialized
        this.output = 0;
    }

    // Forward Propagation
    public void forward (double[] inputs) {
        this.weightedSum = 0;
        // Calculating the dot product of inputs and weights
        for (int i = 0; i < inputs.length; i++) {
            this.weightedSum += inputs[i] * this.weights[i];
        }
        // adding bias
        this.weightedSum += this.bias;
    }

    // Getters
    public double[] getWeights() {
        return this.weights;
    }

    public double getBias() {
        return this.bias;
    }

    public double getWeightedSum() {
        return this.weightedSum;
    }

    public double getOutput() {
        return this.output;
    }

    // Method to update weights
    public void updateWeights(double[] dWeights, double alpha) {
        for (int i = 0; i < this.weights.length; i++) {
            this.weights[i] -= dWeights[i] * alpha;
        }
    }

    // Update Bias
    public void updateBias(double dBias, double alpha) {
        this.bias -= dBias * alpha;
    }

    // Set Output
    public void setOutput(double activatedOutput) {
        this.output = activatedOutput;
    }
}