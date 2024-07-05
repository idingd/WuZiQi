package yiding.wuziqi.view;

import yiding.Main;
import yiding.window.view.AbstractWindow;
import yiding.wuziqi.WuZiQi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class WuZiQiWindow extends AbstractWindow<WuZiQiWindowControl> implements WuZiQiUITemplate {
    private final WuZiQi wuZiQi;

    public WuZiQiWindow(WuZiQi wuZiQi) {
        super("五子棋", new Dimension(800, 800), WuZiQiWindowControl.class);
        this.wuZiQi = wuZiQi;
        controller.wuZiQi = wuZiQi;
        initWindowContent();
    }

    @Override
    public void initWindowContent() {
        add(register("mainboard", new Panel()), panel -> panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                controller.drop(e.getX(), e.getY());
            }
        }));
    }

    @Override
    public void setup() {
        showWindow();
    }

    @Override
    public void GameOver(int state) {
        switch (state) {
            case 0 -> setTitle("白方获胜");
            case 1 -> setTitle("黑方获胜");
            case -1 -> setTitle("平局");
            default -> setTitle(String.format("state：" + state));
        }
    }

    @Override
    public void refresh() {
        controller.refresh();
    }

    class Panel extends JPanel {
        private int horizontalMargin, verticalMargin, smallerMargin, cellSize;

        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            int boardSize = Math.min(Panel.this.getWidth(), Panel.this.getHeight());
            horizontalMargin = (Panel.this.getWidth() - boardSize) / 2;
            verticalMargin = (Panel.this.getHeight() - boardSize) / 2;
            smallerMargin = (boardSize % wuZiQi.getWidth()) / 2;
            cellSize = boardSize / wuZiQi.getWidth();
            for (int i = 1; i <= wuZiQi.getWidth(); i++) {
                g2.drawLine(smallerMargin + horizontalMargin + cellSize - (cellSize / 2), smallerMargin + verticalMargin + cellSize * i - (cellSize / 2), smallerMargin + horizontalMargin + cellSize * wuZiQi.getWidth() - (cellSize / 2), smallerMargin + verticalMargin + cellSize * i - (cellSize / 2));
                g2.drawLine(smallerMargin + horizontalMargin + cellSize * i - (cellSize / 2), smallerMargin + verticalMargin + cellSize - (cellSize / 2), smallerMargin + horizontalMargin + cellSize * i - (cellSize / 2), smallerMargin + verticalMargin + cellSize * wuZiQi.getWidth() - (cellSize / 2));
            }
            if (Main.DEBUG) {
                g2.setColor(Color.CYAN);
                for (int i = 0; i <= wuZiQi.getWidth(); i++) {
                    g2.drawLine(smallerMargin + horizontalMargin, smallerMargin + verticalMargin + cellSize * i, smallerMargin + horizontalMargin + cellSize * wuZiQi.getWidth(), smallerMargin + verticalMargin + cellSize * i);
                    g2.drawLine(smallerMargin + horizontalMargin + cellSize * i, smallerMargin + verticalMargin, smallerMargin + horizontalMargin + cellSize * i, smallerMargin + verticalMargin + cellSize * wuZiQi.getWidth());
                }
                System.out.println(boardSize + Panel.this.getHeight());
                g2.setColor(Color.BLUE);
                g2.drawRect(smallerMargin, smallerMargin, Panel.this.getWidth() - smallerMargin * 2, Panel.this.getHeight() - smallerMargin * 2);
                g2.setColor(Color.GREEN);
                g2.drawRect(smallerMargin + horizontalMargin, smallerMargin + verticalMargin, Panel.this.getWidth() - smallerMargin * 2 - horizontalMargin * 2, Panel.this.getHeight() - smallerMargin * 2 - verticalMargin * 2);
            }
            int chessmanSize = cellSize * 3 / 4;
            for (int i = 0; i < wuZiQi.getWidth(); i++)
                for (int j = 0; j < wuZiQi.getWidth(); j++) {
                    if (wuZiQi.getBoard()[i][j] != -1) {
                        if (wuZiQi.getBoard()[i][j] % 2 == 0) g2.setColor(Color.WHITE);
                        else if (wuZiQi.getBoard()[i][j] % 2 == 1) g2.setColor(Color.BLACK);
                        g2.fillOval(smallerMargin + horizontalMargin - (cellSize / 2) + (cellSize * (i + 1)) - (chessmanSize / 2), smallerMargin + verticalMargin - (cellSize / 2) + (cellSize * (j + 1)) - (chessmanSize / 2), chessmanSize, chessmanSize);
                    }
                    g2.setColor(Color.RED);
                    g2.drawString(String.format("%d %d", i, j), smallerMargin + horizontalMargin - (cellSize / 2) + (cellSize * (i + 1)), smallerMargin + verticalMargin - (cellSize / 2) + (cellSize * (j + 1)));
                }
        }

        public int getHorizontalMargin() {
            return horizontalMargin;
        }

        public int getVerticalMargin() {
            return verticalMargin;
        }

        public int getSmallerMargin() {
            return smallerMargin;
        }

        public int getCellSize() {
            return cellSize;
        }
    }

    @Override
    public void resize() {
        controller.refresh();
    }
}
