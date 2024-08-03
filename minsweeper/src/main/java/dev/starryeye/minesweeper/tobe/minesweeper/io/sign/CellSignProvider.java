package dev.starryeye.minesweeper.tobe.minesweeper.io.sign;

import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;

public interface CellSignProvider {

    boolean supports(CellSnapshot cellSnapshot);

    String provideBy(CellSnapshot cellSnapshot);
}
