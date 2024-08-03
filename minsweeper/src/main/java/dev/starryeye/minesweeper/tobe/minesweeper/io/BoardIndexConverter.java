package dev.starryeye.minesweeper.tobe.minesweeper.io;

import dev.starryeye.minesweeper.tobe.minesweeper.exception.GameException;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndexBy(String selectedCellInput) {
        return convertRowFrom(selectedCellInput.substring(1));
    }

    public int getSelectedColIndexBy(String selectedCellInput) {
        return convertColFrom(selectedCellInput.charAt(0));
    }

    private int convertRowFrom(String enteredCellRow) {

        int rowIndex = Integer.parseInt(enteredCellRow) - 1;
        if (rowIndex < 0) {
            throw new GameException("입력하신 Row, [" + enteredCellRow + "] 값은 잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private int convertColFrom(char enteredCellCol) {

        int colIndex = enteredCellCol - BASE_CHAR_FOR_COL;
        if (colIndex < 0) {
            throw new GameException("입력하신 Column, [" + enteredCellCol + "] 값은 잘못된 입력입니다.");
        }

        return colIndex;
    }
}
