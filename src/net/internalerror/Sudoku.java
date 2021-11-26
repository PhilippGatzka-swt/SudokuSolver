package net.internalerror;

public class Sudoku {

    private final SudokuView sudokuView;
    private int[][] board;
int dif;

    public Sudoku(SudokuView sudokuView, int difficulty) {
        this.sudokuView = sudokuView;
        dif = difficulty;
        prepareBoard();
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setNumber(0, 0);
        sudokuView.printResult(i);
    }

    int i = 0;

    private boolean setNumber(int x, int y) {
        i++;
        if (x == 9) return setNumber(0, y + 1);
        if (y == 9) {
            sudokuView.setComplete();
            sudokuView.setBoard(board);
            return true;
        }
        try {
            Thread.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int n = 1; n < 10; n++) {
            if (isValid(x, y, n)) {
                set(x, y, n);
                if (setNumber(x + 1, y)) {
                    return true;
                } else {
                    set(x, y, 0);
                }
            }
            if (board[y][x] != 0) {
                return setNumber(x + 1, y);
            }
        }
        return false;
    }

    private void set(int x, int y, int n) {
        board[y][x] = n;
        sudokuView.setNew(x, y);
        setBoard();
    }

    private boolean isValid(int x, int y, int n) {
        if (board[y][x] != 0) {
            sudokuView.setReason(x, y);
            return false;
        }

        for (int i = 0; i < 9; i++) {
            if (board[y][i] == n) {
                sudokuView.setReason(i, y);
                return false;
            }
        }
        for (int i = 0; i < 9; i++) {
            if (board[i][x] == n) {
                sudokuView.setReason(y, i);
                return false;
            }
        }
        int cx = (int) Math.floor(x / (float) 3) * 3;
        int cy = (int) Math.floor(y / (float) 3) * 3;

        for (int yy = cy; yy < cy + 3; yy++) {
            for (int xx = cx; xx < cx + 3; xx++) {
                if (board[yy][xx] == n) {
                    sudokuView.setReason(xx, yy);
                    return false;
                }
            }
        }
        return true;
    }

    private void prepareBoard() {
        Generator g = new Generator(9, dif);
        g.fillValues();
        board = g.get();

        // board = new int[][]{
        //         {7, 0, 2, 0, 0, 5, 0, 8, 0},
        //         {0, 0, 1, 0, 0, 0, 0, 0, 0},
        //         {0, 0, 0, 0, 8, 0, 6, 0, 0},
        //         {0, 4, 0, 0, 0, 0, 0, 0, 0},
        //         {0, 0, 0, 3, 0, 0, 0, 0, 9},
        //         {5, 0, 8, 0, 0, 2, 0, 6, 0},
        //         {0, 1, 0, 0, 0, 0, 0, 7, 0},
        //         {4, 0, 7, 2, 0, 0, 3, 0, 0},
        //         {0, 6, 0, 0, 0, 4, 0, 0, 0},
        // };
        setBoard();
    }

    private void setBoard() {
        sudokuView.setBoard(board);
    }
}
