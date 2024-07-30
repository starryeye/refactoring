package dev.starryeye.minesweeper.tobe.position;

import java.util.Objects;

public class CellPosition {

    /**
     * Value Object
     * - 불변성 : final
     * - 동등성 : equals and hashcode
     * - 유효성 검증 : 생성 시점 유효성 검증
     */

    private final int rowIndex;
    private final int colIndex;

    private CellPosition(int rowIndex, int colIndex) {
        if (rowIndex < 0 || colIndex < 0) { // index 로써의 유효성 검증
            throw new IllegalArgumentException("올바른 좌표 값이 아닙니다. row=" + rowIndex + ", col=" + colIndex);
        }

        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    public static CellPosition of(int rowIndex, int colIndex) {
        return new CellPosition(rowIndex, colIndex);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellPosition that = (CellPosition) o;
        return rowIndex == that.rowIndex && colIndex == that.colIndex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowIndex, colIndex);
    }


    public boolean isRowIndexGreaterThanOrEqual(int rowIndex) {
        return this.rowIndex >= rowIndex;
    }

    public boolean isColIndexGreaterThanOrEqual(int colIndex) {
        return this.colIndex >= colIndex;
    }

    public boolean isRowIndexLessThan(int rowIndex) {
        return this.rowIndex < rowIndex;
    }

    public boolean isColIndexLessThan(int colIndex) {
        return this.colIndex < colIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public int getColIndex() {
        return colIndex;
    }

    public boolean canCalculateBy(RelativePosition relativePosition) {
        // CellPosition 생성자 유효성 검증을 통과할 수 있는지를 확인
        return this.rowIndex + relativePosition.getDeltaRow() >= 0
                && this.colIndex + relativePosition.getDeltaCol() >= 0;
    }

    public CellPosition calculateBy(RelativePosition relativePosition) {

        if (canCalculateBy(relativePosition)) {
            return CellPosition.of(
                    this.rowIndex + relativePosition.getDeltaRow(),
                    this.colIndex + relativePosition.getDeltaCol()
            );
        }

        throw new IllegalArgumentException("계산할 수 있는 좌표가 아닙니다.");
    }

}
