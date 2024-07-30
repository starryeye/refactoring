package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.cell.*;
import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;
import dev.starryeye.minesweeper.tobe.position.CellPosition;
import dev.starryeye.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

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

                CellPosition cellPosition = CellPosition.of(row, col);
                if (isLandMineCell(cellPosition)) {
                    continue;
                }
                int count = countNearbyLandMineBasedOn(cellPosition);
                if (count == 0) {
                    continue;
                }
                board[row][col] = new NumberCell(count);
            }
        }
    }

    public void flagAt(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        cell.flag();
    }

    public void openAt(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        cell.open();
    }

    public void openSurroundedCells(CellPosition cellPosition) {

        if (isOpenedCell(cellPosition)) {
            return;
        }
        if (isLandMineCell(cellPosition)) {
            return;
        }

        openAt(cellPosition);

        if (checkAdjacentLandMine(cellPosition)) {
            return;
        }

        List<CellPosition> surroundedPositions = getSurroundedPositions(cellPosition);
        surroundedPositions.forEach(this::openSurroundedCells);
    }

    private boolean checkAdjacentLandMine(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        return cell.hasNearbyLandMine(); // getter 를 참아라..
    }

    private boolean isOpenedCell(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        return cell.isOpened();
    }

    public boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
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

    public boolean isInvalidCellPosition(CellPosition cellPosition) {
        int boardRowSize = getRowSize();
        int boardColSize = getColSize();
        return cellPosition.isRowIndexGreaterThanOrEqual(boardRowSize)
                || cellPosition.isColIndexGreaterThanOrEqual(boardColSize); // getter 의 유혹을 참아라
    }

    public boolean isValidCellPosition(CellPosition cellPosition) {
        int boardRowSize = getRowSize();
        int boardColSize = getColSize();
        return cellPosition.isRowIndexLessThan(boardRowSize)
                && cellPosition.isColIndexLessThan(boardColSize); // getter 의 유혹을 참아라
    }

    public int getRowSize() {
        return board.length;
    }

    public int getColSize() {
        return board[0].length;
    }

    public String getCellSignBy(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        return cell.getSign();
    }

    private Cell getCell(CellPosition cellPosition) {
        return board[cellPosition.getRowIndex()][cellPosition.getColIndex()];
    }

    private int countNearbyLandMineBasedOn(CellPosition cellPosition) {

        long count = getSurroundedPositions(cellPosition).stream()
                .filter(this::isLandMineCell)
                .count();

        return (int) count;
    }

    private List<CellPosition> getSurroundedPositions(CellPosition cellPosition) {
        return RelativePosition.SURROUNDED_POSITIONS.stream()
                .filter(cellPosition::canCalculateBy)
                .map(cellPosition::calculateBy)
                .filter(this::isValidCellPosition)
                .toList();
    }
}
