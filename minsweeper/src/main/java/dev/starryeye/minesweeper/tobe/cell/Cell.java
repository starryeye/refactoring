package dev.starryeye.minesweeper.tobe.cell;

public abstract class Cell {

    protected static final String FLAG_SIGN = "⚑";
    protected static final String UNCHECKED_SIGN = "■";

    protected boolean isFlagged;
    protected boolean isOpened;

    public abstract boolean isLandMine();

    public abstract boolean hasNearbyLandMine();

    public abstract String getSign();

    public void flag() {
        this.isFlagged = true;
    }

    public void open() {
        this.isOpened = true;
    }

    public boolean isChecked() {
        return this.isFlagged || this.isOpened;
    }

    public boolean isFlagged() {
        return this.isFlagged;
    }

    public boolean isOpened() {
        return this.isOpened;
    }
}
