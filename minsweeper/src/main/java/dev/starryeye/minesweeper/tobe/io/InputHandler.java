package dev.starryeye.minesweeper.tobe.io;

import dev.starryeye.minesweeper.tobe.position.CellPosition;
import dev.starryeye.minesweeper.user.UserAction;

public interface InputHandler {

    String getUserInput();

    UserAction getUserActionFromUserInput();

    CellPosition getCellPositionFromUserInput();
}
