package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.config.GameConfig;
import dev.starryeye.minesweeper.tobe.game.GameInitializer;
import dev.starryeye.minesweeper.tobe.game.GameRunner;
import dev.starryeye.minesweeper.tobe.gamelevel.GameLevel;
import dev.starryeye.minesweeper.tobe.io.InputHandler;
import dev.starryeye.minesweeper.tobe.io.OutputHandler;
import dev.starryeye.minesweeper.tobe.position.CellPosition;
import dev.starryeye.minesweeper.user.UserAction;

public class Minesweeper implements GameInitializer, GameRunner { // Game 이라는 하나의 인터페이스로 만들 수도 있지만 만약에 initialize 는 필요없는 Game 이 있다 치면 ISP 위반이다.

    private final GameBoard gameBoard;
    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public Minesweeper(GameLevel gameLevel, InputHandler inputHandler, OutputHandler outputHandler) {
        this.gameBoard = new GameBoard(gameLevel);
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public Minesweeper(GameConfig gameConfig) {
        this.gameBoard = new GameBoard(gameConfig.getGameLevel());
        this.inputHandler = gameConfig.getInputHandler();
        this.outputHandler = gameConfig.getOutputHandler();
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

                CellPosition selectedCellInput = getSelectedCellFromUser();
                UserAction selectedUserActionInput = getSelectedUserActionFromUser();
                actOnCell(selectedCellInput, selectedUserActionInput);
            } catch (GameException e) {
                outputHandler.showGameExceptionMessage(e); // 개발자가 의도한 예외를 처리한다.
            } catch (Exception e) {
                outputHandler.showUnexpectedExceptionMessage(); // 게임을 그대로 진행하는 것이 정책이라 Exception 을 그냥 잡는다. 서버에서는 이렇게 처리하는 것은 안티패턴이며 원래는 던지거나 적절한 처리를 하는게 좋다.
            }
        }
    }

    private void actOnCell(CellPosition selectedCellInput, UserAction selectedUserActionInput) {

        if (doesUserSelectMarkingTheFlag(selectedUserActionInput)) {
            gameBoard.flagAt(selectedCellInput);
            changeGameStatusToWinIfGameClearCondition();
            return;
        }

        if (doesUserSelectOpeningTheCell(selectedUserActionInput)) {
            if (gameBoard.isLandMineCell(selectedCellInput)) {
                gameBoard.openAt(selectedCellInput);
                changeGameStatusToLose();
                return;
            }

            gameBoard.openSurroundedCells(selectedCellInput);
            changeGameStatusToWinIfGameClearCondition();
            return;
        }
        throw new GameException("입력하신 행위, [" + selectedUserActionInput + "] 값은 잘못된 입력입니다.");
    }

    private void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private boolean doesUserSelectOpeningTheCell(UserAction selectedUserActionInput) {
        return UserAction.OPEN == selectedUserActionInput;
    }

    private boolean doesUserSelectMarkingTheFlag(UserAction selectedUserActionInput) {
        return UserAction.FLAG == selectedUserActionInput;
    }

    private UserAction getSelectedUserActionFromUser() {
        outputHandler.showUserActionPromptForSelectedCell();
        return inputHandler.getUserActionFromUserInput();
    }

    private CellPosition getSelectedCellFromUser() {
        outputHandler.showCellSelectionPrompt();
        CellPosition cellPositionFromUserInput = inputHandler.getCellPositionFromUserInput();

        if (gameBoard.isInvalidCellPosition(cellPositionFromUserInput)) {
            // 0 보다 작은 값에 대한 유효성 검증은 CellPosition 생성자에서 처리하였다.(index 개념 자체가 0 보다 작을 수 없기 때문..) 하지만, board size 보다 큰 인덱스 검증은 game board 에서 처리하도록 책임을 분리하였다.
            throw new GameException("잘못된 좌표를 선택하였습니다.");
        }
        return cellPositionFromUserInput;
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
