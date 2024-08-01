package dev.starryeye.minesweeper.tobe.cell;

public enum CellSnapshotStatus {

    UNCHECKED("확인 전, 최초 셀"),
    EMPTY("빈 셀"),
    NUMBER("숫자 셀"),
    FLAG("깃발 셀"),
    LAND_MINE("지뢰 셀")
    ;

    private final String description;

    CellSnapshotStatus(String description) {
        this.description = description;
    }
}
