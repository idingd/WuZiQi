package yiding.wuziqi.view;

import yiding.window.view.AbstractWindow;
import yiding.wuziqi.WuZiQi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WuZiQiWindow extends AbstractWindow<WuZiQiWindowControl> {
    WuZiQi WuZiQi;
    public WuZiQiWindow(WuZiQi WuZiQi) {
        super("Wu Zi Qi", new Dimension(800, 800), WuZiQiWindowControl.class);
        this.WuZiQi = WuZiQi;
        controller.WuZiQi = WuZiQi;
        initWindowContent();
    }

    @Override
    public void initWindowContent() {
        setBackground(new Color(249, 214, 91));
        add(register("mainboard", new Panel()), panel -> panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.isAltDown()) controller.debugger(e.getX(), e.getY());
                else controller.drop(e.getX(), e.getY());
            }
        }));
    }

    class Panel extends JPanel {
        int w, h, cw, ch;
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            w = Panel.this.getWidth() / (WuZiQi.width + 1);
            h = Panel.this.getHeight() / (WuZiQi.width + 1);
            for (int i = 1; i <= WuZiQi.width; i++) {
                g2.drawLine(w, h * i, w * WuZiQi.width, h * i);
                g2.drawLine(w * i, h, w * i, h * WuZiQi.width);
            }
            g2.setColor(Color.cyan);
            for (int i = 0; i <= WuZiQi.width; i++) {
                g2.drawLine(w / 2, h * i + (h / 2), w * WuZiQi.width + (w / 2), h * i + (h / 2));
                g2.drawLine(w * i + (w / 2), h / 2, w * i + (w / 2), h * WuZiQi.width + (h / 2));
            }
            cw = w * 3 / 4;
            ch = h * 3 / 4;
            for (yiding.wuziqi.QiZi qiZi : WuZiQi.board) {
                if (qiZi.state % 2 == 0) g2.setColor(Color.white);
                else if (qiZi.state % 2 == 1) g2.setColor(Color.BLACK);
                g2.fillOval((w * (qiZi.x + 1)) - (cw / 2), (h * (qiZi.y + 1)) - (ch / 2), cw, ch);
            }
        }
    }

    @Override
    public void resize() {
        controller.refresh();
    }
}
