package dev.starryeye.minesweeper.tobe.cell;

public interface Cell {

    String FLAG_SIGN = "⚑";
    String UNCHECKED_SIGN = "■";

    boolean isLandMine();

    boolean hasNearbyLandMine();

    String getSign();

    void flag();

    void open();

    boolean isChecked();

    boolean isFlagged();

    boolean isOpened();
}
