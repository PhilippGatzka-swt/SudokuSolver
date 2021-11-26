package net.internalerror;

public class Main {

    public static void main(String[] args) {
        SudokuView.start();

        /*
         * 0 ... 81
         * easy ... hard
         *
         *
         * 10 min
         * 70 max
         */

        int difficulty = 40;


        Sudoku sudoku = new Sudoku(SudokuView.get(), difficulty);
    }
}
