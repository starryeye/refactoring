package dev.starryeye.minesweeper.tobe.minesweeper.io;

import dev.starryeye.minesweeper.tobe.minesweeper.board.position.CellPosition;
import dev.starryeye.minesweeper.tobe.minesweeper.user.UserAction;

public interface InputHandler {

    String getUserInput();

    UserAction getUserActionFromUserInput();

    CellPosition getCellPositionFromUserInput();
}
