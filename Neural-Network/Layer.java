abstract class Layer {
    // Layer attributes
    protected Neuron[] neurons;
    protected double[] weightedSums;
    protected double[] outputs;

    // Contructor
    public Layer (int numOfNeurons, int numOfInputsPerNeuron) {
        // Create a list of neurons
        this.neurons = new Neuron[numOfNeurons];
        // Initialize neurons
        for (int i = 0; i < numOfNeurons; i++) {
            this.neurons[i] = new Neuron(numOfInputsPerNeuron);
        }
        // Initialize outputs
        this.outputs = new double[this.neurons.length];
        // Initialize weightedSums
        this.weightedSums = new double[this.neurons.length];
    }

    public double[] forward (double[] inputs) {
        // Forward propagating each Neuron with the same inputs
        for (int i = 0; i < this.neurons.length; i++) {
            this.neurons[i].forward(inputs);
            this.weightedSums[i] = this.neurons[i].getWeightedSum();
        }
        // return this.weightedSums;
        this.outputs = this.activation(this.weightedSums);
        return this.outputs;
    }

    // Activation Function
    abstract public double[] activation (double[] weightedSums);

    // Updating weights and biases
    public void updateWeightsAndBiases (double[][] dWeights, double[] dBiases) {
        for (int i = 0; i < this.neurons.length; i++) {
            this.neurons[i].updateWeights(dWeights[i]);
            this.neurons[i].updateBias(dBiases[i]);
        }
    }

    // Get Outputs
    public double[] getOutputs() {
        return this.outputs;
    }

    public double[] getWeightedSums() {
        return this.weightedSums;
    }
}