package dev.starryeye.minesweeper.tobe.io;

import dev.starryeye.minesweeper.tobe.GameBoard;
import dev.starryeye.minesweeper.tobe.GameException;
import dev.starryeye.minesweeper.tobe.position.CellPosition;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler{


    @Override
    public void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    @Override
    public void showBoard(GameBoard board) {

        String alphabets = generateColAlphabets(board);

        System.out.println("    " + alphabets);
        for (int row = 0; row < board.getRowSize(); row++) {
            System.out.printf("%2d  ", row + 1);
            for (int col = 0; col < board.getColSize(); col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                System.out.print(board.getCellSignBy(cellPosition) + " "); // Cell 에게 Board 를 그려달라는 메시지는 Cell 의 책임 범위를 벗어나기 때문에 getter 를 사용해야한다.
            }
            System.out.println();
        }
        System.out.println();
    }

    private static String generateColAlphabets(GameBoard board) {
        List<String> alphabets = IntStream.range(0, board.getColSize())
                .mapToObj(index -> {
                    char c = (char) (index + 'a');
                    return Character.toString(c);
                }).toList();
        return String.join(" ", alphabets); // "a b c d ..."
    }

    @Override
    public void showGameWinMessage() {
        System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
    }

    @Override
    public void showGameLoseMessage() {
        System.out.println("지뢰를 밟았습니다. GAME OVER!");
    }

    @Override
    public void showCellSelectionPrompt() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
    }

    @Override
    public void showUserActionPromptForSelectedCell() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
    }

    @Override
    public void showGameExceptionMessage(GameException e) {
        System.out.println(e.getMessage());
    }

    @Override
    public void showUnexpectedExceptionMessage() {
        System.out.println("프로그램에 문제가 생겼습니다.");
    }
}
