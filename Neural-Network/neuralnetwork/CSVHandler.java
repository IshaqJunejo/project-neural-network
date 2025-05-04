package neuralnetwork;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CSVHandler {
    // Method to read dataset from a .csv file
    public static int[][] readDataFrom (String fileName, int rows, int cols) {
        int data[][] = new int[rows][cols];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int j = 0; j < values.length; j++) {
                    data[i][j] = Integer.parseInt(values[j].trim());
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return data;
    }

    // Method to read weights from a .csv file
    public static double[][] readWeights (String fileName, int rows, int cols) {
        double data[][] = new double[rows][cols];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int j = 0; j < values.length; j++) {
                    data[i][j] = Double.parseDouble(values[j].trim());
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return data;
    }

    // Method to read biases from a .csv file
    public static double[] readBiases (String fileName, int num) {
        double data[] = new double[num];

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int i = 0;

            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",");
                for (int j = 0; j < values.length; j++) {
                    data[j] = Double.parseDouble(values[j].trim());
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        return data;
    }

    // Write a 2D array to a .csv file
    public static void writeDataTo(String fileName, double[][] data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    writer.write(data[i][j] + ",");
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }

    // Write a 1D array to a .csv file
    public static void writeDataTo(String fileName, double[] data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (int i = 0; i < data.length; i++) {
                writer.write(data[i] + ",");
            }
        } catch (IOException e) {
            System.out.println("Error writing file: " + e.getMessage());
        }
    }
}