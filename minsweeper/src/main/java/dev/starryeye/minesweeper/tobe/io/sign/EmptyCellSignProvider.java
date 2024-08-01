package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.cell.CellSnapshotStatus;

public class EmptyCellSignProvider implements CellSignProvider {

    private static final String EMPTY_SIGN = "□";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.EMPTY);
    }

    @Override
    public String provideBy(CellSnapshot cellSnapshot) {
        return EMPTY_SIGN;
    }
}
