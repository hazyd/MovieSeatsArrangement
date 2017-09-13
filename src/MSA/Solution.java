package MSA;

/**
 * Created by hazyd on 9/9/17.
 */

import MSA.SeatsAssigning;
import java.io.*;

public class Solution {
    public static void main(String[] args) {
        SeatsAssigning sa = new SeatsAssigning(10, 20);
        try {
            String inputFilePath = "/Users/hazyd/Desktop/testInput";
            String outputFilePath = "/Users/hazyd/Desktop/testOutput";
            PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
            File file = new File(inputFilePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                while ((lineText = bufferedReader.readLine()) != null) {
                    System.out.println(lineText);
                    String[] input = lineText.split("\\s+");
                    StringBuilder res = new StringBuilder();
                    sa.addSeats(Integer.parseInt(input[1]), res);
                    writer.print(input[0] + " ");
                    writer.println(res.toString());
                }
                writer.close();
                System.out.println("Accomplished!");
            } else {
                System.out.println("Invalid input file name");
            }
        } catch (Exception e) {
            System.out.println("Fail to read file");
            e.printStackTrace();
        }

        sa.printSeats();
    }
}
