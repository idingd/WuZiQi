package yiding.wuziqi;

import yiding.wuziqi.view.WuZiQiUITemplate;

import java.awt.*;

public class WuZiQi {
    private final int width, lian;
    private final int[] players;
    private final int[][] board;
    private int state = 0;
    private WuZiQiUITemplate template;

    public WuZiQi(int a, int b, int width, int lian) {
        if (lian > width) {
            System.err.println("lian (" + lian + ") > width (" + width + ")");
            System.exit(0);
        }
        this.width = width;
        this.lian = lian;
        board = new int[width][width];
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
                board[i][j] = -1;
        players = new int[] {a, b};
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (state >= 0 && players[state % 2] == 1) {
                    Point position = WuZiQiComputer.Computer(this);
                    dropOne(position.x, position.y);
                    checkWin();
                    template.refresh();
                }
            }
        }).start();
    }

    public void dropOne(int x, int y) {
        if ((x >= 0 && x < width && y >= 0 && y < width) && state > -1 && board[x][y] == -1) {
            System.out.println(x + " " + y);
            board[x][y] = ++state;
            checkWin();
            template.refresh();
        }
    }

    public int getWidth() {
        return width;
    }

    public int getLian() {
        return lian;
    }

    public int[] getPlayers() {
        return players;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getState() {
        return state;
    }

    public void setupTemplate(WuZiQiUITemplate template) {
        this.template = template;
        template.setup();
    }

    private int justMore(int x, int y, int cx, int cy) {
        int checkingWin = 0;
        for (int i = 1; i < lian; i++)
            if (onlyOne(x + (i * cx), y + (i * cy)) == 1) checkingWin++;
            else break;
        for (int i = 1; i < lian; i++)
            if (onlyOne(x - (i * cx), y - (i * cy)) == 1) checkingWin++;
            else break;
        return checkingWin;
    }

    private int onlyOne(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= width) return -1;
        if (board[x][y] % 2 == state % 2) return 1;
        else if (board[x][y] == -1) return 0;
        else return -1;
    }

    private void checkWin() {
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
                if (board[i][j] == state) {
                    int[] checkingWins = new int[4];
                    checkingWins[0] = justMore(i, j, 1, 0); // -
                    checkingWins[1] = justMore(i, j, 0, 1); // |
                    checkingWins[2] = justMore(i, j, 1, 1); // \
                    checkingWins[3] = justMore(i, j, 1, -1);// /
                    for (int k = 0; k < 4; k++) {
                        if (state % 2 != -1 && (checkingWins[k] >= lian - 1 || state == width * width)) {
                            System.out.println("Game Over!");
                            if (state == width * width) state = -1;
                            template.GameOver(state % 2);
                            state = -1;
                            return;
                        }
                    }
                }
    }
}