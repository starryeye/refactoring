package dev.starryeye.minesweeper.tobe.minesweeper.io.sign;

import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

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
