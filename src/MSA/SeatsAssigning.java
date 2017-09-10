package MSA;

/**
 * Created by hazyd on 9/9/17.
 */
import java.util.List;
import java.util.ArrayList;

public class SeatsAssigning {
    int[][] seats;
    int row;
    int col;
    int startingRow = 0;
    char[] mapChar = new char[]{'J','I','H','G','F','E','D','C','B','A'};

    public SeatsAssigning(int r, int c) {
        this.row = r;
        this.col = c;
        seats = new int[r][c];
    }

    public List<String> addSeats(int n) {
        List<String> res = new ArrayList<>();
        for (int i = this.startingRow; i < this.row; i++) {
            int startingCol = checkStartingCol(i, n);
            if (startingCol == -1) {
                continue;
            } else {
                for (int j = 0; j < n; j++) {
                    this.seats[i][startingCol + j] = 1;
                    StringBuilder sb = new StringBuilder();
                    sb.append(this.mapChar[i]);
                    sb.append(startingCol + j + 1);
                    res.add(sb.toString());
                }
                break;
            }
        }
        for (int i = 0; i < res.size(); i++) {
            System.out.println(res.get(i));
        }
        return res;
    }

    public void printSeats() {
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.seats[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int checkStartingCol(int rowNum, int n) {
        int count = 0;
        for (int i = this.col - 1; i >= 0; i--) {
            if (this.seats[rowNum][i] == 1) {
                break;
            } else {
                count++;
            }
        }
        if (count + 1 < n) {
            return -1;
        } else {
            return this.col == count ? 0 : this.col - (count - 1);
        }
    }
}
