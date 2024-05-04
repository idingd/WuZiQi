package yiding.wuziqi;

import java.util.ArrayList;

public class WuZiQi {
    //x=---,y=|
    public int width = 15, lian = 5;
    public int state = 0;
    public int[][] checkWin = new int[4][2];
    public ArrayList<QiZi> board = new ArrayList<>();
    public int[] players = new int[2];
    public WuZiQi(int a, int b) {
        if (lian > width) {
            System.err.println("lian (" + lian + ") > width (" + width + ")");
            System.exit(0);
        }
        players[0] = a;
        players[1] = b;
        new Thread(() -> {
            while (true){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                if (state >= 0 && players[state % 2] == 1) {
                    //todo:computer
                    System.out.println("no computer");
                }
            }
        }).start();
    }

    public void dropOne(int x, int y, boolean isTest) {
        if (NoArrayOutOfBoardException(x) && NoArrayOutOfBoardException(y) && state > -1 && getOne(x, y) == null) {
            if (!isTest) {
                state++;
                board.add(new QiZi(x, y, state));
            }
            for (int i = 0;i<4;i++)
                for (int j = 0; j < 2; j++)
                    checkWin[i][j] = 0;
            justMore(x, y, 1, 0, 0);
            justMore(x, y, 0, 1, 1);
            justMore(x, y, 1, 1, 2);
            justMore(x, y, 1, -1, 3);
        }
    }

    public void justMore(int x, int y, int cx, int cy, int num) {
        for (int i = 1; i < lian; i++)
            if (onlyOne(x + (i * cx), y + (i * cy)) == 1) checkWin[num][0]++;
            else break;
        for (int i = 1; i < lian; i++)
            if (onlyOne(x + ((-1 * i) * cx), y + ((-1 * i) * cy)) == 1) checkWin[num][1]++;
            else break;
    }

    private int onlyOne(int x, int y) {
        if (getOne(x, y) == null) return 0;
        else if (getOne(x, y).state % 2 == state % 2) return 1;
        else return -1;
    }

    public QiZi getOne(int x, int y) {
        for (QiZi qiZi : board)
            if (qiZi.x == x && qiZi.y == y) return qiZi;
        return null;
    }

    private int calculate(int i) {
        return (int) Math.pow(lian, i);
    }

    public boolean NoArrayOutOfBoardException(int i) {
        return i >= 0 && i <= width - 1;
    }

    public void checkWin() {
        for (int i = 0; i < 4; i++)
            if (state % 2 != -1 && (checkWin[i][0] + checkWin[i][1] >= lian - 1 || state == width * width)) {
                System.out.println("Game Over!");
                state = -1;
                return;
            }
    }
}