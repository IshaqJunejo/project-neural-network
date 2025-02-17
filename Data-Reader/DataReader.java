import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class DataReader {
    // Read data from a file
    private static int[][] readData(String fileName, int rows, int cols) {
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

    // 
    public static void main(String[] args) {
        int testData[][] = new int[10000][785];
        // Reading the data from the file
        testData = readData("mnist_test.csv", 10000, 785);
        // Selecting a random index
        int index = (int)(Math.random() * 10000);

        // Printing the label
        System.out.println("Label: " + testData[index][0]);
        // Visualising the image (using ASCII Characters)
        for (int i = 1; i < 785; i++) {
            char val;

            if (testData[index][i] >= 0 && testData[index][i] <= 30) {
                val = ' ';
            } else if (testData[index][i] >= 31 && testData[index][i] <= 60) {
                val = '.';
            } else if (testData[index][i] >= 61 && testData[index][i] <= 90) {
                val = ',';
            } else if (testData[index][i] >= 61 && testData[index][i] <= 90) {
                val = ',';
            } else if (testData[index][i] >= 91 && testData[index][i] <= 120) {
                val = '-';
            } else if (testData[index][i] >= 121 && testData[index][i] <= 150) {
                val = '=';
            } else if (testData[index][i] >= 151 && testData[index][i] <= 180) {
                val = '+';
            } else if (testData[index][i] >= 181 && testData[index][i] <= 210) {
                val = '*';
            } else if (testData[index][i] >= 211 && testData[index][i] <= 240) {
                val = '#';
            } else {
                val = '@';
            }

            System.out.print(val + " ");
            // Printing a new line after every 28 characters
            if (i % 28 == 0) {
                System.out.println();
            }
        }
    }
}