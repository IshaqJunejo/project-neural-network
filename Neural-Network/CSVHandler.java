import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class CSVHandler {
    // Read data from a .csv file
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
}