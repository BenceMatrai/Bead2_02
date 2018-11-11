import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Model {

    private int size;

    private Player actualPlayer;
    private int numOfTurns;

    private Player[][] table;

    public Player[][] getTable() {
        return table;
    }

    public Model(int size) {
        this.size = size;
        this.actualPlayer = Player.O;
        this.numOfTurns = size * 5;
        this.table = generateLayout(size);

    }

    public int getNumOfTurns() {
        return numOfTurns;
    }

    private Player[][] generateLayout(int size) {
        Player[][] map = new Player[size][size];
        ArrayList<Integer> nums = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            nums.add(0);
        }
        for (int i = 0; i < size; i++) {
            nums.add(1);
        }
        for (int i = 0; i < ((size * size) - (2 * size)); i++) {
            nums.add(2);
        }

        Random rand = new Random();
        int r, c;
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                r = rand.nextInt(nums.size());
                c = nums.remove(r);
                if (c == 0) map[i][j] = Player.X;
                if (c == 1) map[i][j] = Player.O;
                if (c == 2) map[i][j] = Player.NOBODY;
            }
        }

        return map;
    }

    public Boolean select(int row, int column) {
        if (table[row][column] == Player.NOBODY) {
            return false;
        }

        if (actualPlayer == Player.X && table[row][column].name().equals("X")) {
            return true;
        }
        else return actualPlayer == Player.O && table[row][column].name().equals("O");

    }

    public void move(int n, Point curr) {

        int x = curr.x;
        int y = curr.y;
        Player temp = table[x][y];
        Player temp2;
        table[x][y] = Player.NOBODY;

        if (n == 0) {
            while ((y - 1) >= 0 && table[x][y - 1] != Player.NOBODY) {
                temp2 = table[x][y - 1];
                table[x][y - 1] = temp;
                temp = temp2;
                y--;
            }
            if ((y - 1) >= 0) table[x][y - 1] = temp;
        }
        else if (n == 1) {
            while ((x - 1) >= 0 && table[x - 1][y] != Player.NOBODY) {
                temp2 = table[x - 1][y];
                table[x - 1][y] = temp;
                temp = temp2;
                x--;
            }
            if ((x - 1) >= 0) table[x - 1][y] = temp;
        }
        else if (n == 2) {
            while ((y + 1) < size && table[x][y + 1] != Player.NOBODY) {
                temp2 = table[x][y + 1];
                table[x][y + 1] = temp;
                temp = temp2;
                y++;
            }
            if ((y + 1) < size) table[x][y + 1] = temp;
        }
        else if (n == 3) {
            while ((x + 1) < size && table[x + 1][y] != Player.NOBODY) {
                temp2 = table[x + 1][y];
                table[x + 1][y] = temp;
                temp = temp2;
                x++;
            }
            if ((x + 1) < size) table[x + 1][y] = temp;
        }

        if (actualPlayer == Player.X) {
            actualPlayer = Player.O;
        }
        else actualPlayer = Player.X;

        numOfTurns--;

    }


    public Player findWinner() {

        int onum = 0, xnum = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {

                if (table[i][j] == Player.O) { onum++; }
                else if (table[i][j] == Player.X) { xnum++; }

            }
        }

        if (onum == 0) { return Player.X; }
        else if (xnum == 0) { return Player.O; }

        if (numOfTurns == 0) {
            if (xnum > onum) return Player.X;
            if (onum > xnum) return Player.O;
            return Player.DRAW;
        }

        return Player.NOBODY;
    }

    public Player getActualPlayer() {
        return actualPlayer;
    }

}
