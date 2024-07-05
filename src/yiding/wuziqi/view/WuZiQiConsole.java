package yiding.wuziqi.view;

import yiding.wuziqi.WuZiQi;

import java.util.Scanner;

public class WuZiQiConsole implements WuZiQiUITemplate{

    private final WuZiQi wuZiQi;

    public WuZiQiConsole(WuZiQi wuZiQi) {
        this.wuZiQi = wuZiQi;
    }

    @Override
    public void setup() {
        if (wuZiQi.getWidth() > 999) throw new RuntimeException(String.format("width (%d) > 999", wuZiQi.getWidth()));
        refresh();
    }

    @Override
    public void GameOver(int state) {
        switch (state) {
            case 0 -> System.out.println("白方获胜");
            case 1 -> System.out.println("黑方获胜");
            case -1 -> System.out.println("平局");
            default -> System.out.printf("state：" + state + "%n");
        }
    }

    @Override
    public void refresh() {
        for (int i = -1; i < wuZiQi.getWidth(); i++) System.out.printf("%-3d", i + 1);
        System.out.println();
        for (int i = 0; i < wuZiQi.getWidth(); i++) {
            System.out.printf("%-3d", i + 1);
            for (int j = 0; j < wuZiQi.getWidth(); j++) {
                switch (wuZiQi.getBoard()[j][i] % 2) {
                    case 1 -> System.out.print("○  ");
                    case 0 -> System.out.print("●  ");
                    case -1 -> System.out.print(".  ");
                }
            }
            System.out.println();
        }
        if (wuZiQi.getState()>= 0 && wuZiQi.getPlayers()[wuZiQi.getState() % 2] == 0) {
            Scanner scanner = new Scanner(System.in);
            String[] ss = scanner.nextLine().split(" ");
            int x = Integer.parseInt(ss[0]), y = Integer.parseInt(ss[1]);
            wuZiQi.dropOne(x - 1, y - 1);
        }
    }
}
