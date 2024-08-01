package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.cell.CellSnapshotStatus;

public class FlagCellSignProvider implements CellSignProvider {

    private static final String FLAG_SIGN = "⚑";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.FLAG);
    }

    @Override
    public String provideBy(CellSnapshot cellSnapshot) {
        return FLAG_SIGN;
    }
}
