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
            String inputFilePath = args[0];
            String outputFilePath = "/Users/hazyd/Desktop/testOutput";
            PrintWriter writer = new PrintWriter(outputFilePath, "UTF-8");
            File file = new File(inputFilePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file));
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineText = null;
                while ((lineText = bufferedReader.readLine()) != null) {
                    String[] input = lineText.split("\\s+");
                    StringBuilder res = new StringBuilder();
                    sa.addSeats(Integer.parseInt(input[1]), res);
                    res.setLength(res.length() - 1);
                    writer.print(input[0] + " ");
                    writer.println(res.toString());
                }
                writer.close();
                System.out.println("The output file path is: " + outputFilePath);
            } else {
                System.out.println("Invalid input file name");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //sa.printA();
        sa.printSeats();
    }
}
