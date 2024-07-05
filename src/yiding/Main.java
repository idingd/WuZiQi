package yiding;

import yiding.wuziqi.WuZiQi;
import yiding.wuziqi.view.WuZiQiConsole;
import yiding.wuziqi.view.WuZiQiWindow;

public class Main {
    public static boolean DEBUG = true;
    public static void main(String[] args) {
        int width = 15, lian = 5;
        boolean cli = false;
        if (args.length != 0) {
            for (int i = 0; i < args.length; i++) {
                switch (args[i]) {
                    case "-w", "--width" -> {
                        if (i + 1 < args.length) {
                            width = Integer.parseInt(args[i + 1]);
                            i++;
                        }
                    }
                    case "-l", "--lian" -> {
                        if (i + 1 < args.length) {
                            lian = Integer.parseInt(args[i + 1]);
                            i++;
                        }
                    }
                    case "-c", "--cli" -> cli = true;
                    case "-g", "--gui" -> cli = false;
                    default -> System.err.printf("无效选项：%s %n", args[i]);
                }
            }
        }
        WuZiQi wuZiQi = new WuZiQi(0, 0, width, lian);
        if (cli) wuZiQi.setupTemplate(new WuZiQiConsole(wuZiQi));
        else wuZiQi.setupTemplate(new WuZiQiWindow(wuZiQi));
    }
}