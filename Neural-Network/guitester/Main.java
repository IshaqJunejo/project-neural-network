package guitester;

import neuralnetwork.*;
import java.awt.*;
import javax.swing.*;

class Main {
    public static void main (String[] args) {
        // Setting up the Neural Network
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
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        DrawingBoard board = new DrawingBoard();

        JPanel controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(0, 50));

        JPanel predictionPanel = new JPanel();
        predictionPanel.setPreferredSize(new Dimension(300, 250));
        predictionPanel.setLayout(new GridLayout(11, 1));

        // Buttons
        JButton clear = new JButton("CLEAR");
        controlPanel.add(clear);

        JButton predict = new JButton("PREDICT");
        controlPanel.add(predict);

        // Predictions
        JLabel title = new JLabel("Predictions: ", JLabel.LEFT);
        title.setBounds(50, 20, 150, 20);
        predictionPanel.add(title);
        JLabel predictions[] = new JLabel[10];

        for (int i = 0; i < predictions.length; i++) {
            predictions[i] = new JLabel(i + " : 0.00 %", JLabel.LEFT);
            predictions[i].setBounds(50, 40 + i * 20, 150, 20);
            predictionPanel.add(predictions[i]);
        }

        predictionPanel.revalidate();
        predictionPanel.repaint();

        // Adding control to buttons
        predict.addActionListener(e -> {
            predict(layer1, layer2, layer3, board, predictions);
        });

        clear.addActionListener(e -> {
            cleanSlate(predictions, board);
        });

        frame.setLayout(new BorderLayout());
        frame.add(board, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.CENTER);
        frame.add(predictionPanel, BorderLayout.SOUTH);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void predict (HiddenLayer layer1, HiddenLayer layer2, OutputLayer layer3, DrawingBoard board, JLabel[] labels) {
        double[] input = board.getInput();
        double[] hidden1 = layer1.forward(input);
        double[] hidden2 = layer2.forward(hidden1);
        double[] predictions = layer3.forward(hidden2);

        // Update the prediction labels
        updatePredictions(predictions, labels);
    }

    private static void updatePredictions(double[] predictions, JLabel[] labels) {
        for (int i = 0; i < predictions.length; i++) {
            labels[i].setText(i + " : " + String.format("%.2f", predictions[i] * 100) + " %");
        }
    }

    private static void cleanSlate (JLabel[] labels, DrawingBoard board) {
        board.clearBoard();
        board.repaint();
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(i + " : 0.00 %");
        }
    }
}