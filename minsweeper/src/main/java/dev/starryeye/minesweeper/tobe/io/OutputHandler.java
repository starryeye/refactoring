package dev.starryeye.minesweeper.tobe.io;

import dev.starryeye.minesweeper.tobe.GameBoard;
import dev.starryeye.minesweeper.tobe.GameException;

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
