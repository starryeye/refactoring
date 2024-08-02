package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.cell.*;
import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;
import dev.starryeye.minesweeper.tobe.position.CellPosition;
import dev.starryeye.minesweeper.tobe.position.CellPositions;
import dev.starryeye.minesweeper.tobe.position.RelativePosition;

import java.util.Arrays;
import java.util.List;

public class GameBoard {

    private final int landMineCount;
    private final Cell[][] board;
    private GameStatus gameStatus;

    public GameBoard(GameLevel gameLevel) {
        int rowSize = gameLevel.getRowSize();
        int colSize = gameLevel.getColSize();

        this.landMineCount = gameLevel.getLandMineCount();
        this.board = new Cell[rowSize][colSize];
        initializeGameStatus();
    }

    public void initializeGame() {

        initializeGameStatus();

        // 주의, cellPositions 가 board 를 대채하는 건 아니다. board 는 Cell 을 가지고 있지만, CellPositions 는 CellPosition 들을 가지고 있음
        CellPositions allCellPositions = CellPositions.from(board);

        initializeEmptyCell(allCellPositions);

        CellPositions landMineCellPositions = allCellPositions.extractRandomCellPositions(landMineCount);
        initializeLandMineCell(landMineCellPositions);

        CellPositions numberCellPositionCandidates = allCellPositions.subtract(landMineCellPositions);
        initializeNumberCell(numberCellPositionCandidates);
    }

    private void initializeGameStatus() {
        this.gameStatus = GameStatus.IN_PROGRESS;
    }

    private void initializeEmptyCell(CellPositions emptyCellPositions) {
        emptyCellPositions.getAll()
                .forEach(emptyCellPosition -> updateCellAt(emptyCellPosition, new EmptyCell()));
    }

    private void initializeLandMineCell(CellPositions landMineCellPositions) {
        landMineCellPositions.getAll()
                .forEach(landMineCellPosition -> updateCellAt(landMineCellPosition, new LandMineCell()));
    }

    private void initializeNumberCell(CellPositions numberCellPositionCandidates) {
        numberCellPositionCandidates.getAll()
                .forEach(numberCellPositionCandidate -> {
                    int count = countNearbyLandMineBasedOn(numberCellPositionCandidate);
                    if (count > 0) {
                        updateCellAt(numberCellPositionCandidate, new NumberCell(count));
                    }
                });
    }

    public void markFlagAndCheckWinCondition(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        cell.flag();

        changeGameStatusToWinIfGameClearCondition();
    }

    private void changeGameStatusToWinIfGameClearCondition() {
        if (checkGameClearCondition()) {
            changeGameStatusToWin();
        }
    }

    private boolean checkGameClearCondition() {
        return isAllCellChecked() && isAllLandMinesFlagged();
    }

    private void changeGameStatusToWin() {
        gameStatus = GameStatus.WIN;
    }

    private void openAt(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        cell.open();
    }

    private void openSurroundedCells(CellPosition cellPosition) {

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

    private boolean isLandMineCell(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        return cell.isLandMine();
    }

    private boolean isAllCellChecked() {
        Cells cells = Cells.from(board);
        return cells.isAllChecked();
    }

    private boolean isAllLandMinesFlagged() { // todo, Cells 사용
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

    private boolean isValidCellPosition(CellPosition cellPosition) {
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

    public CellSnapshot getCellSnapshotBy(CellPosition cellPosition) {
        Cell cell = getCell(cellPosition);
        return cell.getSnapshot();
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

    private void updateCellAt(CellPosition cellPosition, Cell cell) {
        board[cellPosition.getRowIndex()][cellPosition.getColIndex()] = cell;
    }

    public boolean isInProgressStatus() {
        return this.gameStatus == GameStatus.IN_PROGRESS;
    }

    public boolean isLoseStatus() {
        return gameStatus == GameStatus.LOSE;
    }

    public boolean isWinStatus() {
        return gameStatus == GameStatus.WIN;
    }

    public void openCellAndCheckEndCondition(CellPosition selectedCellInput) {
        if (isLandMineCell(selectedCellInput)) {
            openAt(selectedCellInput);
            changeGameStatusToLose();
            return;
        }

        openSurroundedCells(selectedCellInput);
        changeGameStatusToWinIfGameClearCondition();
    }

    private void changeGameStatusToLose() {
        gameStatus = GameStatus.LOSE;
    }
}
