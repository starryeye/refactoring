package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.game.GameInitializer;
import dev.starryeye.minesweeper.tobe.game.GameRunner;
import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;
import dev.starryeye.minesweeper.tobe.io.InputHandler;
import dev.starryeye.minesweeper.tobe.io.OutputHandler;

public class Minesweeper implements GameInitializer, GameRunner { // Game 이라는 하나의 인터페이스로 만들 수도 있지만 만약에 initialize 는 필요없는 Game 이 있다 치면 ISP 위반이다.

    private final GameBoard gameBoard;
    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        this.gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    @Override
    public void initialize() {
        gameBoard.initializeGame();
    }

    @Override
    public void run() {
        outputHandler.showGameStartComments();

        while (true) {
            try {
                outputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    outputHandler.showGameWinMessage();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    outputHandler.showGameLoseMessage();
                    break;
                }

                String selectedCellInput = getSelectedCellFromUser();
                String selectedUserActionInput = getSelectedUserActionFromUser();
                actOnCell(selectedCellInput, selectedUserActionInput);
            } catch (GameException e) {
                outputHandler.showGameExceptionMessage(e); // 개발자가 의도한 예외를 처리한다.
            } catch (Exception e) {
                outputHandler.showUnexpectedExceptionMessage(); // 게임을 그대로 진행하는 것이 정책이라 Exception 을 그냥 잡는다. 서버에서는 이렇게 처리하는 것은 안티패턴이며 원래는 던지거나 적절한 처리를 하는게 좋다.
            }
        }
    }

    private void actOnCell(String selectedCellInput, String selectedUserActionInput) {

        int selectedColIndex = boardIndexConverter.getSelectedColIndexBy(selectedCellInput, gameBoard.getColSize()); // 메서드명에 전치사를 사용함으로써 파라미터와 연결지어 의미를 자연스럽게 전달할 수 있다.
        int selectedRowIndex = boardIndexConverter.getSelectedRowIndexBy(selectedCellInput, gameBoard.getRowSize());

        if (doesUserSelectMarkingTheFlag(selectedUserActionInput)) {
            gameBoard.flag(selectedRowIndex, selectedColIndex);
            changeGameStatusToWinIfGameClearCondition();
            return;
        }

        if (doesUserSelectOpeningTheCell(selectedUserActionInput)) {
            if (gameBoard.isLandMineCell(selectedRowIndex, selectedColIndex)) {
                gameBoard.open(selectedRowIndex, selectedColIndex);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedRowIndex, selectedColIndex);
            changeGameStatusToWinIfGameClearCondition();
            return;
        }
        throw new GameException("입력하신 행위, [" + selectedUserActionInput + "] 값은 잘못된 입력입니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserSelectOpeningTheCell(String selectedUserActionInput) {
        return selectedUserActionInput.equals("1");
    }

    private boolean doesUserSelectMarkingTheFlag(String selectedUserActionInput) {
        return selectedUserActionInput.equals("2");
    }

    private String getSelectedUserActionFromUser() {
        outputHandler.showUserActionPromptForSelectedCell();
        return inputHandler.getUserInput();
    }

    private String getSelectedCellFromUser() {
        outputHandler.showCellSelectionPrompt();
        return inputHandler.getUserInput();
    }

    private boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private void changeGameStatusToWinIfGameClearCondition() {
        if (checkGameClearCondition()) {
            changeGameStatusToWin();
        }
    }

    private boolean checkGameClearCondition() {
        return gameBoard.isAllCellChecked() && gameBoard.isAllLandMinesFlagged();
    }

    private void changeGameStatusToWin() {
        gameStatus = 1;
    }
}
