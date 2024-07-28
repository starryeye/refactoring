package dev.starryeye.minesweeper.tobe;

public class BoardIndexConverter {

    private static final char BASE_CHAR_FOR_COL = 'a';

    public int getSelectedRowIndexBy(String selectedCellInput, int rowSize) {
        return convertRowFrom(selectedCellInput.substring(1), rowSize);
    }

    public int getSelectedColIndexBy(String selectedCellInput, int colSize) {
        return convertColFrom(selectedCellInput.charAt(0), colSize);
    }

    private int convertRowFrom(String enteredCellRow, int rowSize) {

        int rowIndex = Integer.parseInt(enteredCellRow) - 1;
        if (rowIndex < 0 || rowIndex >= rowSize) {
            throw new GameException("입력하신 Row, [" + enteredCellRow + "] 값은 잘못된 입력입니다.");
        }

        return rowIndex;
    }

    private int convertColFrom(char enteredCellCol, int colSize) {

        int colIndex = enteredCellCol - BASE_CHAR_FOR_COL;
        if (colIndex < 0 || colIndex >= colSize) {
            throw new GameException("입력하신 Column, [" + enteredCellCol + "] 값은 잘못된 입력입니다.");
        }

        return colIndex;
    }
}
