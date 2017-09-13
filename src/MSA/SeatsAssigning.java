package MSA;

/**
 * Created by hazyd on 9/9/17.
 */
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SeatsAssigning {
    int[][] seats;
    int row;
    int col;
    int startingRow = 0;
    char[] mapChar = new char[]{'J','I','H','G','F','E','D','C','B','A'};
    Map<Integer, List<String>> availableSeats;
    int curRow = 0;

    public SeatsAssigning(int r, int c) {
        this.row = r;
        this.col = c;
        this.seats = new int[r][c];
        this.availableSeats = new HashMap<>();
        for (int i = 0; i < this.row; i++) {
            List<String> list = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            sb.append(0);
            sb.append(' ');
            sb.append(this.col);
            list.add(sb.toString());
            this.availableSeats.put(i, list);
        }
    }

    public void addSeats(int n, StringBuilder res) {
        if (n > checkAllAvailableSeats()) {
            System.out.print("Error! There's no plenty of seats!");
        } else {
            boolean isComplete = false;
            if (n > this.col) {
                int c = n / 2;
                addSeats(c, res);
                addSeats(n - c, res);
            } else {
                int count = 0;
                while (count < (this.row / 2)) {
                    this.curRow = this.curRow % (this.row / 2);
                    int startPlace = checkMaxGapStartPos(n);
                    if (startPlace == -1) {
                        this.curRow++;
                        count++;
                    } else {
                        assignSeats(startPlace, n, res);
                        this.curRow++;
                        isComplete = true;
                        break;
                    }
                }
                if (count == (this.row / 2)) {
                    this.curRow = this.row / 2;
                    while (this.curRow < this.row) {
                        int startPlace = checkMaxGapStartPos(n);
                        if (startPlace == -1) {
                            this.curRow++;
                        } else {
                            assignSeats(startPlace, n, res);
                            isComplete = true;
                            break;
                        }
                    }
                    this.curRow = 0;
                }
                if (!isComplete) {
                    if (checkAllAvailableSeats() >= n) {
                        int assignedNum = 0;
                        for (int i = 0; i < this.row; i++) {
                            for (int j = 0; j < this.col; j++) {
                                if (assignedNum < n && this.seats[i][j] == 0) {
                                    this.seats[i][j] = 1;
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(this.mapChar[i]);
                                    sb.append(j + 1);
                                    sb.append(" ");
                                    res.append(sb.toString());
                                    assignedNum++;
                                }
                            }
                        }
                    } else {
                        res.append("Error! There's no plenty of seats!");
                    }
                }
            }
        }
    }

    public void assignSeats(int startPlace, int n, StringBuilder res) {
        int emptySeats = countEmpty(startPlace);
        int newStarter = startPlace + (emptySeats - n) / 2;
        printResult(n, newStarter, res);
        this.availableSeats.put(this.curRow, update());
    }

    public int countEmpty(int startPos) {
        int emptySeatsNum = 0;
        for (int i = startPos; i < this.col; i++) {
            if (this.seats[this.curRow][i] == 0) {
                emptySeatsNum++;
            } else {
                break;
            }
        }
        return emptySeatsNum;
    }


    public void printResult(int count, int startPos, StringBuilder res) {
        for (int i = 0; i < count; i++) {
            this.seats[this.curRow][startPos + i] = 1;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mapChar[this.curRow]);
            sb.append(startPos + i);
            sb.append(" ");
            res.append(sb.toString());
        }
    }

    public List<String> update() {
        List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < this.col; i++) {
            if (this.seats[this.curRow][i] == 0) {
                continue;
            } else {
                if (start == i) {
                    start++;
                    continue;
                }
                StringBuilder sb = new StringBuilder();
                sb.append(start);
                sb.append(" ");
                sb.append(i);
                list.add(sb.toString());
                start = i + 1;
            }
        }
        if (start != this.col) {
            StringBuilder sb = new StringBuilder();
            sb.append(start);
            sb.append(" ");
            sb.append(this.col);
            list.add(sb.toString());
        }
        return list;
    }


    public void printSeats() {
        System.out.println();
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                System.out.print(this.seats[i][j] + " ");
            }
            System.out.println();
        }
    }

    public int checkMaxGapStartPos(int seatsNum) {
        List<String> list = this.availableSeats.get(this.curRow);
        int gap = 0;
        int maxGapStart =  -1;
        for (int i = 0; i < list.size(); i++) {
            String[] interval = list.get(i).split("\\s+");
            int s = (int)Integer.valueOf(interval[0]);
            int e = (int)Integer.valueOf(interval[1]);
            if (e - s < seatsNum) {
                continue;
            } else {
                if (gap <= e - s - seatsNum) {
                    gap = e - s - seatsNum;
                    maxGapStart = s;
                }
            }
        }
        return maxGapStart;
    }

    public int checkAllAvailableSeats() {
        int num = 0;
        for (int i = 0; i < this.row; i++) {
            for (int j = 0; j < this.col; j++) {
                if (this.seats[i][j] == 0) {
                    num++;
                }
            }
        }
        return num;
    }
}
