package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;

public interface CellSignProvider {

    boolean supports(CellSnapshot cellSnapshot);

    String provideBy(CellSnapshot cellSnapshot);
}
