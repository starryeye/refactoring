package dev.starryeye.minesweeper.tobe.minesweeper.io;

import dev.starryeye.minesweeper.tobe.minesweeper.board.GameBoard;
import dev.starryeye.minesweeper.tobe.minesweeper.exception.GameException;

public interface OutputHandler {

    void showGameStartComments();

    void showBoard(GameBoard board);

    void showGameWinMessage();

    void showGameLoseMessage();

    void showCellSelectionPrompt();

    void showUserActionPromptForSelectedCell();

    void showGameExceptionMessage(GameException e);

    void showUnexpectedExceptionMessage();
}
