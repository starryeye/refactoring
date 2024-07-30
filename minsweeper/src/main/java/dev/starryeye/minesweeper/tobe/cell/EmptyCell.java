package dev.starryeye.minesweeper.tobe.cell;

public class EmptyCell implements Cell {

    private static final String EMPTY_SIGN = "□";

    private final CellState cellState = CellState.initialize();

    @Override
    public boolean isLandMine() {
        return false;
    }

    @Override
    public boolean hasNearbyLandMine() {
        return false;
    }

    @Override
    public String getSign() {
        if (cellState.isOpened()) {
            return EMPTY_SIGN;
        }
        if (cellState.isFlagged()) {
            return FLAG_SIGN;
        }

        return UNCHECKED_SIGN;
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
