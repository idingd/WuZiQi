package yiding.wuziqi.view;

import yiding.wuziqi.QiZi;
import yiding.wuziqi.WuZiQi;

public class WuZiQiWindowControl extends yiding.window.contol.AbstractWindowControl {
    WuZiQi WuZiQi;
    public void refresh() {
        WuZiQiWindow.Panel mainboard = getComponent("mainboard");
        mainboard.repaint();
    }

    public void drop(int x, int y) {
        if (WuZiQi.state >= 0 && WuZiQi.players[WuZiQi.state % 2] == 0) {
            WuZiQiWindow.Panel mainboard = getComponent("mainboard");
            int px = (x - (mainboard.w / 2)) / mainboard.w;
            int py = (y - (mainboard.h / 2)) / mainboard.h;
            WuZiQi.dropOne(px, py, false);
            WuZiQi.checkWin();
            refresh();
        }
    }

    public void debugger(int x, int y) {
        WuZiQiWindow.Panel mainboard = getComponent("mainboard");
        int px = (x - (mainboard.w / 2)) / mainboard.w;
        int py = (y - (mainboard.h / 2)) / mainboard.h;
        QiZi qiZi = WuZiQi.getOne(px, py);
        System.out.println(qiZi);
    }
}
