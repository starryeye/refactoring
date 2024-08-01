package dev.starryeye.minesweeper.tobe.io;

public enum ConsoleCellSign {

    UNCHECKED("■"),
    EMPTY("□"),
    FLAG("⚑"),
    NUMBER(""),
    LAND_MINE("☼");

    private final String sign;

    ConsoleCellSign(String sign) {
        this.sign = sign;
    }

    public String sign() {
        return this.sign;
    }

    public String sign(int nearbyLandMineCount) {

        if (this == NUMBER) {
            return String.valueOf(nearbyLandMineCount);
        }

        return this.sign;
    }
}
