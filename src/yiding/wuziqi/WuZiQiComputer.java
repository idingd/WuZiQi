package yiding.wuziqi;

import java.awt.*;
import java.util.ArrayList;

public class WuZiQiComputer {
    private static int[][] board;
    private static int state;
    private static int width;
    private static int me;
    private static int lian;

    public static Point Computer(WuZiQi wuZiQi) {
        WuZiQiComputer.board = wuZiQi.getBoard();
        WuZiQiComputer.state = wuZiQi.getState();
        WuZiQiComputer.lian = wuZiQi.getLian();
        WuZiQiComputer.me = (state+1) % 2;
        WuZiQiComputer.width = board.length;
        System.err.println(me);
        return run();
    }

    private static QiZi run() {
        ArrayList<QiZi> qiZis = getBlankPoint();
        QiZi biggestQiZi = new QiZi(-1, -1);
        biggestQiZi.setScore(Integer.MIN_VALUE);
        for (QiZi qiZi : qiZis) {
            dropOne(qiZi);
            qiZi.score += evaluate(qiZi);
            clearOne(qiZi);
//            state--;
//            dropOne(qiZi);
//            qiZi.score -= evaluate(qiZi) - 1;
//            clearOne(qiZi);
//            state++;
            if (biggestQiZi.score < qiZi.score) {
                biggestQiZi = qiZi;
            }
            System.err.println(qiZi);
        }
        return biggestQiZi;
    }

    static ArrayList<QiZi> getBlankPoint() {
        ArrayList<QiZi> blankPoints = new ArrayList<>();
        for (int i = 0; i < width; i++)
            for (int j = 0; j < width; j++)
                if (board[i][j] == -1) {
                    blankPoints.add(new QiZi(i, j));
                }
        return blankPoints;
    }

    static void dropOne(Point qiZi) {
        state++;
        board[qiZi.x][qiZi.y] = state;
    }

    static void clearOne(Point qiZi) {
        state--;
        board[qiZi.x][qiZi.y] = -1;
    }

    static int evaluate(QiZi qiZi) {
        int[][] checkingWins = new int[4][2];
        checkingWins[0] = justMore(qiZi.x, qiZi.y, 1, 0); // -
        checkingWins[1] = justMore(qiZi.x, qiZi.y, 0, 1); // |
        checkingWins[2] = justMore(qiZi.x, qiZi.y, 1, 1); // \
        checkingWins[3] = justMore(qiZi.x, qiZi.y, 1, -1);// /
        int score = 0;
        for (int k = 0; k < 4; k++) {
            score += calculate(checkingWins[k][0] - ((double) checkingWins[k][1] / 2));
        }
        return score;
    }

    static int calculate(double i) {
        if (i <= 0) return 0;
        int result = 1;
        for (double j = 0; j < i; j+=0.5) {
            result *= lian;
        }
        return result;
    }

    private static int[] justMore(int x, int y, int cx, int cy) {
        int checkingWin = 0;
        int sleepQiZi = 0;
        for (int i = 1; i < lian; i++) {
            int result = onlyOne(x + (i * cx), y + (i * cy));
            if (result == 1) checkingWin++;
            else if (result == -1) {sleepQiZi++; break;}
            else break;
        }
        for (int i = 1; i < lian; i++) {
            int result = onlyOne(x + ((-1 * i) * cx), y + ((-1 * i) * cy));
            if (result == 1) checkingWin++;
            else if (result == -1) {sleepQiZi++; break;}
            else break;
        }
        return new int[] {checkingWin, sleepQiZi};
    }

    private static int onlyOne(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= width) return -1;
        if (board[x][y] % 2 == state % 2) return 1;
        else if (board[x][y] == -1) return 0;
        else return -1;
    }

    private static class QiZi extends Point {
        private int score;

        public QiZi(int x, int y) {
            super(x, y);
        }

        @Override
        public String toString() {
            return getClass().getName() + String.format("[x=%3d, y=%3d, score=%6d]", x, y, score);
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }
    }
}
