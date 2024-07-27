package dev.starryeye.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private static final int LAND_MINE_COUNT = 10;

    private final Cell[][] board;

    public GameBoard(int rowSize, int colSize) {
        this.board = new Cell[rowSize][colSize];
    }

    public void initializeGame() {

        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            Cell landMineCell = getCell(landMineRow, landMineCol);
            landMineCell.turnOnLandMine();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {

                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMineBasedOn(row, col);
                getCell(row, col).updateNearbyLandMineCount(count);
            }
        }
    }

    public void flag(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        cell.flag();
    }

    public void open(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        cell.open();
    }

    public void openSurroundedCells(int row, int col) {
        int rowSize = getRowSize();
        int colSize = getColSize();

        if (row < 0 || row >= rowSize || col < 0 || col >= colSize) {
            return;
        }
        if (isOpenedCell(row, col)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        open(row, col);

        if (checkAdjacentLandMine(row, col)) {
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

    private boolean checkAdjacentLandMine(int row, int col) {
        Cell cell = getCell(row, col);
        return cell.hasNearbyLandMine(); // getter 를 참아라..
    }

    private boolean isOpenedCell(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return cell.isOpened();
    }

    public boolean isLandMineCell(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return cell.isLandMine();
    }

    public boolean isAllCellChecked() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked); // getter 로 비교하는 것을 참고 객체에게 메시지를 던져서 객체가 스스로 판단하도록 하자
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getCellSignBy(int rowIndex, int colIndex) {
        Cell cell = getCell(rowIndex, colIndex);
        return cell.getSign();
    }

    private Cell getCell(int rowIndex, int colIndex) {
        return board[rowIndex][colIndex];
    }

    private int countNearbyLandMineBasedOn(int row, int col) {

        int rowSize = getRowSize();
        int colSize = getColSize();

        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < colSize && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < colSize && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < rowSize && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < rowSize && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < rowSize && col + 1 < colSize && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

}
