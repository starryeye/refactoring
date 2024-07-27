package dev.starryeye.minesweeper.tobe.io;

import dev.starryeye.minesweeper.tobe.GameBoard;
import dev.starryeye.minesweeper.tobe.GameException;

public class ConsoleOutputHandler {


    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    public void showBoard(GameBoard board) {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                System.out.print(board.getCellSignBy(row, col) + " "); // Cell 에게 Board 를 그려달라는 메시지는 Cell 의 책임 범위를 벗어나기 때문에 getter 를 사용해야한다.
            }
            System.out.println();
        }
        System.out.println();
    }

    public void printGameWinMessage() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    public void printGameLoseMessage() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    public void printCellSelectionPrompt() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    public void printUserActionPromptForSelectedCell() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    public void printGameExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    public void printUnexpectedExceptionMessage() {
        System.out.println("프로그램에 문제가 생겼습니다.");
    }
}
