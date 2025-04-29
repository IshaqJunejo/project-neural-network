package guitester;

import neuralnetwork.*;
import java.awt.*;
import javax.swing.*;

class Main {
    public static void main (String[] args) {
        //System.out.println("Hello World");

        HiddenLayer layer1 = new HiddenLayer((28 * 28), 16);
        HiddenLayer layer2 = new HiddenLayer(16, 16);
        OutputLayer layer3 = new OutputLayer(16, 10);

        // Read parameters from .csv files
        double[][] weights1 = CSVHandler.readWeights("weights1.csv", 16, (28 * 28));
        double[] biases1 = CSVHandler.readBiases("biases1.csv", 16);

        double[][] weights2 = CSVHandler.readWeights("weights2.csv", 16, 16);
        double[] biases2 = CSVHandler.readBiases("biases2.csv", 16);

        double[][] weights3 = CSVHandler.readWeights("weights3.csv", 10, 16);
        double[] biases3 = CSVHandler.readBiases("biases3.csv", 10);

        // Setting parameter values of our Neural Network
        layer1.setWeights(weights1);
        layer1.setBiases(biases1);

        layer2.setWeights(weights2);
        layer2.setBiases(biases2);

        layer3.setWeights(weights3);
        layer3.setBiases(biases3);

        // Setting up the GUI
        JFrame frame = new JFrame("Neural Network");
        DrawingBoard board = new DrawingBoard();

        frame.setLayout(new BorderLayout());
        frame.add(board, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}