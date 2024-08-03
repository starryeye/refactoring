package dev.starryeye.minesweeper.tobe.minesweeper.io;

import dev.starryeye.minesweeper.tobe.minesweeper.board.GameBoard;
import dev.starryeye.minesweeper.tobe.minesweeper.exception.GameException;
import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;
import dev.starryeye.minesweeper.tobe.minesweeper.board.position.CellPosition;
import dev.starryeye.minesweeper.tobe.minesweeper.io.sign.CellSignFinder;
import dev.starryeye.minesweeper.tobe.minesweeper.io.sign.CellSignTemplate;

import java.util.List;
import java.util.stream.IntStream;

public class ConsoleOutputHandler implements OutputHandler {

    private final CellSignTemplate cellSignTemplate = new CellSignTemplate();

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

                CellSnapshot cellSnapshot = board.getCellSnapshotBy(cellPosition);
                /**
                 * 단순 Enum 사용한 방법, 클래스 관점에서 OCP 만족 못함
                 *      String cellSign = decideCellSignBasedOn(cellSnapshot);
                 * template callback pattern(현재 default callback 사용) 사용한 방법, 클래스 관점에서 OCP 만족
                 *      String cellSign = cellSignTemplate.findCellSignBy(cellSnapshot);
                 */
                String cellSign = CellSignFinder.findCellSignBy(cellSnapshot); // 위 두가지 방법을 섞은 중간 단계, 클래스 관점에서 OCP 만족 못함

                System.out.print(cellSign + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private String decideCellSignBasedOn(CellSnapshot cellSnapshot) {

        CellSnapshotStatus status = cellSnapshot.getStatus();

        return switch (status) {
            case UNCHECKED -> ConsoleCellSign.UNCHECKED.sign();
            case EMPTY -> ConsoleCellSign.EMPTY.sign();
            case FLAG -> ConsoleCellSign.FLAG.sign();
            case NUMBER -> ConsoleCellSign.NUMBER.sign(cellSnapshot.getNearbyLandMineCount());
            case LAND_MINE -> ConsoleCellSign.LAND_MINE.sign();
        };
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
