package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.cell.CellSnapshotStatus;

public class NumberCellSignProvider implements CellSignProvider {

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(CellSnapshotStatus.NUMBER);
    }

    @Override
    public String provideBy(CellSnapshot cellSnapshot) {
        int nearbyLandMineCount = cellSnapshot.getNearbyLandMineCount();
        return String.valueOf(nearbyLandMineCount);
    }
}
