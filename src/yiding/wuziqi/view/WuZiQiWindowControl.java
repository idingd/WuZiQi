package yiding.wuziqi.view;

import yiding.wuziqi.WuZiQi;

public class WuZiQiWindowControl extends yiding.window.contol.AbstractWindowControl {
    protected WuZiQi wuZiQi;

    public void refresh() {
        WuZiQiWindow.Panel mainboard = getComponent("mainboard");
        mainboard.repaint();
    }

    public void drop(int x, int y) {
        if (wuZiQi.getState() >= 0 && wuZiQi.getPlayers()[wuZiQi.getState() % 2] == 0) {
            WuZiQiWindow.Panel mainboard = getComponent("mainboard");
            wuZiQi.dropOne((x - mainboard.getHorizontalMargin() - mainboard.getSmallerMargin()) / mainboard.getCellSize(), (y - mainboard.getVerticalMargin() - mainboard.getSmallerMargin()) / mainboard.getCellSize());
        }
    }
}
