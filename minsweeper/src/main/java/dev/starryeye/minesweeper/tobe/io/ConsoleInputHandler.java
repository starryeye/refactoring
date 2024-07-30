package dev.starryeye.minesweeper.tobe.io;

import dev.starryeye.minesweeper.tobe.BoardIndexConverter;
import dev.starryeye.minesweeper.tobe.position.CellPosition;

import java.util.Scanner;

public class ConsoleInputHandler implements InputHandler{

    public static final Scanner SCANNER = new Scanner(System.in);

    private final BoardIndexConverter boardIndexConverter = new BoardIndexConverter();

    @Override
    public String getUserInput() {
        return SCANNER.nextLine();
    }

    @Override
    public CellPosition getCellPositionFromUserInput() {

        String userInput = SCANNER.nextLine();

        int rowIndex = boardIndexConverter.getSelectedRowIndexBy(userInput); // 메서드명에 전치사를 사용함으로써 파라미터와 연결지어 의미를 자연스럽게 전달할 수 있다.
        int colIndex = boardIndexConverter.getSelectedColIndexBy(userInput);
        return CellPosition.of(rowIndex, colIndex);
    }
}
