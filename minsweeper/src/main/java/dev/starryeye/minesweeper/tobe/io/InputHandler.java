package dev.starryeye.minesweeper.tobe.io;

import dev.starryeye.minesweeper.tobe.position.CellPosition;

public interface InputHandler {

    String getUserInput();

    CellPosition getCellPositionFromUserInput();
}
