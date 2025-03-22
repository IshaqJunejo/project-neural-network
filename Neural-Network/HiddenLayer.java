class HiddenLayer extends Layer {
    // Constructor
    public HiddenLayer(int numOfInputsPerNeuron, int numOfNeurons) {
        super(numOfInputsPerNeuron, numOfNeurons);
    }

    // Activation Function
    @Override
    public double[] activation(double[] weightedSums) {
        for (int i = 0; i < weightedSums.length; i++) {
            this.outputs[i] = this.reLU(weightedSums[i]);
        }
        return this.outputs;
    }

    private double reLU(double x) {
        return (x > 0) ? x : 0;
    }
}