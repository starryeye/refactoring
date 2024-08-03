package dev.starryeye.minesweeper.tobe.minesweeper.board.cell;

public interface Cell {

    boolean isLandMine();

    boolean hasNearbyLandMine();

    CellSnapshot getSnapshot();

    void flag();

    void open();

    boolean isChecked();

    boolean isFlagged();

    boolean isOpened();
}
