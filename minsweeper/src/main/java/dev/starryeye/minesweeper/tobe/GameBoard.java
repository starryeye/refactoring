package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.cell.Cell;
import dev.starryeye.minesweeper.tobe.cell.EmptyCell;
import dev.starryeye.minesweeper.tobe.cell.LandMineCell;
import dev.starryeye.minesweeper.tobe.cell.NumberCell;
import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;

import java.util.Arrays;
import java.util.Random;

public class GameBoard {

    private final int landMineCount;
    private final Cell[][] board;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();

        this.landMineCount = gameLevel.getLandMineCount();
        this.board = new Cell[rowSize][colSize];
    }

    public void initializeGame() {

        int rowSize = getRowSize();
        int colSize = getColSize();

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {
                board[row][col] = new EmptyCell();
            }
        }

        for (int i = 0; i < landMineCount; i++) { // todo, 지뢰가 동일한 지점에 중복으로 지정될 수 있음
            int landMineCol = new Random().nextInt(colSize);
            int landMineRow = new Random().nextInt(rowSize);
            board[landMineRow][landMineCol] = new LandMineCell();
        }

        for (int row = 0; row < rowSize; row++) {
            for (int col = 0; col < colSize; col++) {

                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMineBasedOn(row, col);
                if (count == 0) {
                    continue;
                }
                board[row][col] = new NumberCell(count);
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

        openSurroundedCells(row - 1, col - 1);
        openSurroundedCells(row - 1, col);
        openSurroundedCells(row - 1, col + 1);
        openSurroundedCells(row, col - 1);
        openSurroundedCells(row, col + 1);
        openSurroundedCells(row + 1, col - 1);
        openSurroundedCells(row + 1, col);
        openSurroundedCells(row + 1, col + 1);
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

    public boolean isAllLandMinesFlagged() {
        return Arrays.stream(board)
                .flatMap(Arrays::stream)
                .filter(Cell::isLandMine)
                .allMatch(Cell::isFlagged);
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
