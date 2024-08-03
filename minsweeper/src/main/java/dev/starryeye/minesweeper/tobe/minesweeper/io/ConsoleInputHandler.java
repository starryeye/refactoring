package dev.starryeye.minesweeper.tobe.minesweeper.io;

import dev.starryeye.minesweeper.tobe.minesweeper.board.position.CellPosition;
import dev.starryeye.minesweeper.tobe.minesweeper.user.UserAction;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler{

    public static final Scanner SCANNER = new Scanner(System.in);

    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }

    @Override
    public UserAction getUserActionFromUserInput() {

        String userInput = SCANNER.nextLine();

        if ("1".equals(userInput)) { // NPE 방지를 위해 상수의 equals 를 사용
            return UserAction.OPEN;
        }
        if ("2".equals(userInput)) {
            return UserAction.FLAG;
        }

        return UserAction.UNKNOWN;
    }

    @Override
    public CellPosition getCellPositionFromUserInput() {

        String userInput = SCANNER.nextLine();

        int rowIndex = boardIndexConverter.getSelectedRowIndexBy(userInput); // 메서드명에 전치사를 사용함으로써 파라미터와 연결지어 의미를 자연스럽게 전달할 수 있다.
        int colIndex = boardIndexConverter.getSelectedColIndexBy(userInput);
        return CellPosition.of(rowIndex, colIndex);
    }
}
