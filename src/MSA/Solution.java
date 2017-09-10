package MSA;

/**
 * Created by hazyd on 9/9/17.
 */

import MSA.SeatsAssigning;

public class Solution {
    public static void main(String[] args) {
        SeatsAssigning sa = new SeatsAssigning(10, 20);
        sa.addSeats(21);
        sa.printSeats();
    }
}
