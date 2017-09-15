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
    //This is the map to demonstrate how many seats are available.
    //By default, it looks like 1 -> 1 20. 2 -> 1 20. 3 -> 1 20. etc.
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
        this.curRow = this.row / 2 - 1;
    }

    public void addSeats(int n, StringBuilder res) {
        if (n <= 0) {
            res.append("Invalid order! ");
        } else if (n > checkAllAvailableSeats()) {
            res.append("Error! There's no plenty of seats! ");
        } else {
            boolean isComplete = false;
            if (n > this.col) {
                int c = n / 2;
                addSeats(c, res);
                addSeats(n - c, res);
            } else {
                int count = 0;
                //Looking for contiguous available seats in the last five rows
                while (count < (this.row / 2)) {
                    this.curRow = this.curRow < 0? this.row / 2 - 1: this.curRow;
                    int startPlace = checkMaxGapStartPos(n);
                    if (startPlace == -1) {
                        this.curRow--;
                        count++;
                    } else {
                        assignSeats(startPlace, n, res);
                        this.curRow--;
                        isComplete = true;
                        break;
                    }
                }
                //If the last five rows have been fully filled
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
                //If there is no contiguous available seats
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
                                    sb.append(",");
                                    res.append(sb.toString());
                                    assignedNum++;
                                }
                            }
                        }
                        for (int i = 0; i < this.row; i++) {
                            this.availableSeats.put(i, update(i));
                        }
                    } else {
                        res.append("Error! There's no plenty of seats!");
                    }
                }
            }
        }
    }

    //Assign seats from the startPlace.
    public void assignSeats(int startPlace, int n, StringBuilder res) {
        int emptySeats = countEmpty(startPlace);
        int newStarter = startPlace + (emptySeats - n) / 2;
        printResult(n, newStarter, res);
        this.availableSeats.put(this.curRow, update(this.curRow));
    }

    //Count how many seats are available in the current row.
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

    //Print the assigning result into the stringBuilder for output.
    public void printResult(int count, int startPos, StringBuilder res) {
        for (int i = 0; i < count; i++) {
            this.seats[this.curRow][startPos + i] = 1;
            StringBuilder sb = new StringBuilder();
            sb.append(this.mapChar[this.curRow]);
            sb.append(startPos + i + 1);
            sb.append(",");
            res.append(sb.toString());
        }
    }

    //Update the availability of the Row r.
    public List<String> update(int r) {
        List<String> list = new ArrayList<>();
        int start = 0;
        for (int i = 0; i < this.col; i++) {
            if (this.seats[r][i] == 0) {
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

    //Print out the current seats.
    public void printSeats() {
        System.out.println();
        for (int i = 0; i < this.row; i++) {
            System.out.print(this.mapChar[i] + ": ");
            for (int j = 0; j < this.col; j++) {
                System.out.print((this.seats[i][j] == 0? "O" : "X")+ " ");
            }
            System.out.println();
        }
    }

    //Check the maximum number of available seats and return the starting position of the max gap
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

    //Check how many seats are still available in the whole theater
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

//    public void printA() {
//        for (int i = 0; i < this.row; i++) {
//            List<String> list = this.availableSeats.get(i);
//            for (int j = 0; j < list.size(); j++) {
//                System.out.print(list.get(j) + " and ");
//            }
//            System.out.println();
//        }
//    }
}
