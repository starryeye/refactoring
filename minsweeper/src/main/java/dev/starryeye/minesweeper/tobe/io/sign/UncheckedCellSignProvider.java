package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.cell.CellSnapshotStatus;

public class UncheckedCellSignProvider implements CellSignProvider {

    private static final String UNCHECKED_SIGN = "■";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.UNCHECKED);
    }

    @Override
    public String provideBy(CellSnapshot cellSnapshot) {
        return UNCHECKED_SIGN;
    }
}
