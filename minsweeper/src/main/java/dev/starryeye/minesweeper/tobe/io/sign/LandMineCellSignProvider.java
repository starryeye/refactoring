package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.cell.CellSnapshotStatus;

public class LandMineCellSignProvider implements CellSignProvider {

    private static final String LAND_MINE_SIGN = "☼";

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.LAND_MINE);
    }

    @Override
    public String provideBy(CellSnapshot cellSnapshot) {
        return LAND_MINE_SIGN;
    }
}
