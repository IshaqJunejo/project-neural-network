package neuralnetwork;

abstract class Layer {
    // Layer attributes
    protected Neuron[] neurons;
    protected double[] weightedSums;
    protected double[] outputs;

    // Contructor
    public Layer (int numOfInputsPerNeuron, int numOfNeurons) {
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
    public void updateWeightsAndBiases (double[][] dWeights, double[] dBiases, double alpha) {
        for (int i = 0; i < this.neurons.length; i++) {
            this.neurons[i].updateWeights(dWeights[i], alpha);
            this.neurons[i].updateBias(dBiases[i], alpha);
        }
    }

    // Setter Functions
    public void setWeights (double[][] w) {
        for (int i = 0; i < this.neurons.length; i++) {
            this.neurons[i].setWeights(w[i]);
        }
    }

    public void setBiases (double[] b) {
        for (int i =0; i < this.neurons.length; i++) {
            this.neurons[i].setBias(b[i]);
        }
    }

    // Getter Functions
    public double[] getOutputs() {
        return this.outputs;
    }

    public double[] getWeightedSums() {
        return this.weightedSums;
    }

    public double[][] getWeights() {
        double[][] weights = new double[this.neurons.length][this.neurons[0].getWeights().length];
        for (int i = 0; i < this.neurons.length; i++) {
            weights[i] = this.neurons[i].getWeights();
        }
        return weights;
    }

    public double[] getBiases() {
        double[] bias = new double[this.neurons.length];
        for (int i = 0; i < bias.length; i++) {
            bias[i] = this.neurons[i].getBias();
        }
        return bias;
    }
}