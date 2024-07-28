package dev.starryeye.minesweeper.tobe;

import dev.starryeye.minesweeper.tobe.io.ConsoleInputHandler;
import dev.starryeye.minesweeper.tobe.io.ConsoleOutputHandler;

public class Minesweeper {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;

    private final GameBoard gameBoard = new GameBoard(BOARD_ROW_SIZE, BOARD_COL_SIZE);
    private final ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler();
    private final ConsoleOutputHandler consoleOutputHandler = new ConsoleOutputHandler();
    private int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public void run() {
        consoleOutputHandler.showGameStartComments();
        gameBoard.initializeGame();

        while (true) {
            try {
                consoleOutputHandler.showBoard(gameBoard);

                if (doesUserWinTheGame()) {
                    consoleOutputHandler.printGameWinMessage();
                    break;
                }
                if (doesUserLoseTheGame()) {
                    consoleOutputHandler.printGameLoseMessage();
                    break;
                }

                String selectedCellInput = getSelectedCellFromUser();
                String selectedUserActionInput = getSelectedUserActionFromUser();
                actOnCell(selectedCellInput, selectedUserActionInput);
            } catch (GameException e) {
                consoleOutputHandler.printGameExceptionMessage(e); // 개발자가 의도한 예외를 처리한다.
            } catch (Exception e) {
                consoleOutputHandler.printUnexpectedExceptionMessage(); // 게임을 그대로 진행하는 것이 정책이라 Exception 을 그냥 잡는다. 서버에서는 이렇게 처리하는 것은 안티패턴이며 원래는 던지거나 적절한 처리를 하는게 좋다.
            }
        }
    }

    private void actOnCell(String selectedCellInput, String selectedUserActionInput) {

        int selectedColIndex = getSelectedColIndexBy(selectedCellInput); // 메서드명에 전치사를 사용함으로써 파라미터와 연결지어 의미를 자연스럽게 전달할 수 있다.
        int selectedRowIndex = getSelectedRowIndexBy(selectedCellInput);

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

    private int getSelectedRowIndexBy(String selectedCellInput) {
        return convertRowFrom(selectedCellInput.charAt(1));
    }

    private int getSelectedColIndexBy(String selectedCellInput) {
        return convertColFrom(selectedCellInput.charAt(0));
    }

    private String getSelectedUserActionFromUser() {
        consoleOutputHandler.printUserActionPromptForSelectedCell();
        return consoleInputHandler.getUserInput();
    }

    private String getSelectedCellFromUser() {
        consoleOutputHandler.printCellSelectionPrompt();
        return consoleInputHandler.getUserInput();
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

    private int convertRowFrom(char enteredCellRow) {
        int rowIndex = Character.getNumericValue(enteredCellRow) - 1;
        if (rowIndex < 0 || rowIndex >= BOARD_ROW_SIZE) {
            throw new GameException("입력하신 Row, [" + enteredCellRow + "] 값은 잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private int convertColFrom(char enteredCellCol) {
        return switch (enteredCellCol) {
            case 'a' -> 0;
            case 'b' -> 1;
            case 'c' -> 2;
            case 'd' -> 3;
            case 'e' -> 4;
            case 'f' -> 5;
            case 'g' -> 6;
            case 'h' -> 7;
            case 'i' -> 8;
            case 'j' -> 9;
            default -> throw new GameException("입력하신 Column, [" + enteredCellCol + "] 값은 잘못된 입력입니다.");
        };
    }
}
