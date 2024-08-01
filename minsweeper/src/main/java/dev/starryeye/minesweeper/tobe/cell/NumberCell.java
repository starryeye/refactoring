package dev.starryeye.minesweeper.tobe.cell;

public class NumberCell implements Cell {

    private final int nearbyLandMineCount;

    private final CellState cellState = CellState.initialize();

    public NumberCell(int nearbyLandMineCount) {
        this.nearbyLandMineCount = nearbyLandMineCount;
    }

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasNearbyLandMine() {
        return true;
    }

    @Override
    public CellSnapshot getSnapshot() {
        if (cellState.isOpened()) {
            return CellSnapshot.ofNumber(nearbyLandMineCount);
        }
        if (cellState.isFlagged()) {
            return CellSnapshot.ofFlag();
        }

        return CellSnapshot.ofUnchecked();
    }

    @Override
    public void flag() {
        cellState.flag();
    }

    @Override
    public void open() {
        cellState.open();
    }

    @Override
    public boolean isChecked() {
        return cellState.isChecked();
    }

    @Override
    public boolean isFlagged() {
        return cellState.isFlagged();
    }

    @Override
    public boolean isOpened() {
        return cellState.isOpened();
    }
}
