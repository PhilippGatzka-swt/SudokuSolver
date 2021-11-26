package net.internalerror;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class SudokuView extends JPanel {

    private static SudokuView sudokuView;
    private int[][] board;

    boolean complete = false;
    int cellsize = 100;
    int rx = -1;
    int ry = -1;
    int nx = -1;
    int ny = -1;
    private int result = -1;


    public static void start() {
        JFrame frame = new JFrame();
        sudokuView = new SudokuView();
        frame.add(sudokuView);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(919, 1080));
    }

    public SudokuView() {
        setSize(new Dimension(900, 900));
    }

    public static SudokuView get() {
        return sudokuView;
    }

    public void setBoard(int[][] board) {
        this.board = board;
        repaint();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println(width + " " + +height);
        super.resize(width, height);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        if (complete) {
            g2.setColor(Color.green);
        } else {
            g2.setColor(Color.white);
        }
        g2.fillRect(0, 0, 900, 900);
        g2.setColor(Color.black);
        if (board == null) return;
        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                g2.drawRect(x * cellsize, y * cellsize, cellsize, cellsize);
            }
        }

        for (int y = 0; y < board.length / 3; y++) {
            for (int x = 0; x < board[y].length / 3; x++) {
                float thickness = 5;
                Stroke oldStroke = g2.getStroke();
                g2.setStroke(new BasicStroke(thickness));
                g2.drawRect(x * (cellsize * 3), y * (cellsize * 3), cellsize * 3, cellsize * 3);
                g2.setStroke(oldStroke);
            }
        }

        for (int y = 0; y < board.length; y++) {
            for (int x = 0; x < board[y].length; x++) {
                int val = board[y][x];
                if (val == 0) continue;
                if (x == rx && y == ry) {
                    g2.setColor(new Color(1, 0, 0, .5f));
                    if (rx == nx) {
                        g2.fillRect(x * cellsize, 0, x * cellsize + cellsize, 900);
                    }
                    if (ry == ny) {
                        g2.fillRect(0, y * cellsize, 900, cellsize);
                    }
                    g2.setColor(Color.red);
                    rx = -1;
                    ry = -1;
                    try {
                        Thread.sleep(4);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                if (x == nx && y == ny) {
                    g2.setColor(Color.blue);
                    nx = -1;
                    ny = -1;
                }
                g2.setFont(new Font("System Bold", Font.BOLD, 90));
                int tx = x * cellsize + g2.getFontMetrics().stringWidth(val + "") / 2;
                int ty = y * cellsize + 85;
                g2.drawString(val + "", tx, ty);
                g.setColor(Color.black);
            }
        }

        if (result == -1) return;
        g2.setColor(Color.black);
        g2.drawString(result + " Operations", 100, 1000);
    }

    public void setComplete() {
        complete = true;
    }

    public void setNew(int xx, int yy) {
        nx = xx;
        ny = yy;
    }

    public void setReason(int xx, int yy) {
        rx = xx;
        ry = yy;
    }

    public void printResult(int i) {
        result = i;
    }
}
