package dev.starryeye.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final Cell[][] BOARD = new Cell[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;

    private static int gameStatus = 0; // 0: 게임 중, 1: 승리, -1: 패배

    public static void main(String[] args) {

        showGameStartComments();
        initializeGame();

        while (true) {
            try {
                showBoard();

                if (doesUserWinTheGame()) {
                    System.out.println("지뢰를 모두 찾았습니다. GAME CLEAR!");
                    break;
                }
                if (doesUserLoseTheGame()) {
                    System.out.println("지뢰를 밟았습니다. GAME OVER!");
                    break;
                }

                String selectedCellInput = getSelectedCellFromUser();
                String selectedUserActionInput = getSelectedUserActionFromUser();
                actOnCell(selectedCellInput, selectedUserActionInput);
            } catch (AppException e) {
                System.out.println(e.getMessage()); // 개발자가 의도한 예외를 처리한다.
            } catch (Exception e) {
                System.out.println("프로그램에 문제가 생겼습니다."); // 게임을 그대로 진행하는 것이 정책이라 Exception 을 그냥 잡는다. 서버에서는 이렇게 처리하는 것은 안티패턴이며 원래는 던지거나 적절한 처리를 하는게 좋다.
            }
        }
    }

    private static void actOnCell(String selectedCellInput, String selectedUserActionInput) {

        int selectedColIndex = getSelectedColIndexBy(selectedCellInput); // 메서드명에 전치사를 사용함으로써 파라미터와 연결지어 의미를 자연스럽게 전달할 수 있다.
        int selectedRowIndex = getSelectedRowIndexBy(selectedCellInput);

        if (doesUserSelectMarkingTheFlag(selectedUserActionInput)) {
            BOARD[selectedRowIndex][selectedColIndex].flag();
            changeGameStatusToWinIfAllCellIsChecked();
            return;
        }

        if (doesUserSelectOpeningTheCell(selectedUserActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex].open();
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            changeGameStatusToWinIfAllCellIsChecked();
            return;
        }
        throw new AppException("입력하신 행위, [" + selectedUserActionInput + "] 값은 잘못된 입력입니다.");
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return BOARD[selectedRowIndex][selectedColIndex].isLandMine();
    }

    private static boolean doesUserSelectOpeningTheCell(String selectedUserActionInput) {
        return selectedUserActionInput.equals("1");
    }

    private static boolean doesUserSelectMarkingTheFlag(String selectedUserActionInput) {
        return selectedUserActionInput.equals("2");
    }

    private static int getSelectedRowIndexBy(String selectedCellInput) {
        return convertRowFrom(selectedCellInput.charAt(1));
    }

    private static int getSelectedColIndexBy(String selectedCellInput) {
        return convertColFrom(selectedCellInput.charAt(0));
    }

    private static String getSelectedUserActionFromUser() {
        System.out.println("선택한 셀에 대한 행위를 선택하세요. (1: 오픈, 2: 깃발 꽂기)");
        return SCANNER.nextLine();
    }

    private static String getSelectedCellFromUser() {
        System.out.println("선택할 좌표를 입력하세요. (예: a1)");
        return SCANNER.nextLine();
    }

    private static boolean doesUserLoseTheGame() {
        return gameStatus == -1;
    }

    private static boolean doesUserWinTheGame() {
        return gameStatus == 1;
    }

    private static void changeGameStatusToWinIfAllCellIsChecked() { // checkGameClearCondition
        if (isAllCellChecked()) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellChecked() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .allMatch(Cell::isChecked); // getter 로 비교하는 것을 참고 객체에게 메시지를 던져서 객체가 스스로 판단하도록 하자
    }

    private static int convertRowFrom(char enteredCellRow) {
        int rowIndex = Character.getNumericValue(enteredCellRow) - 1;
        if (rowIndex < 0 || rowIndex >= BOARD_ROW_SIZE) {
            throw new AppException("입력하신 Row, [" + enteredCellRow + "] 값은 잘못된 입력입니다.");
        }
        return rowIndex;
    }

    private static int convertColFrom(char enteredCellCol) {
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
            default -> throw new AppException("입력하신 Column, [" + enteredCellCol + "] 값은 잘못된 입력입니다.");
        };
    }

    private static void showBoard() {
        System.out.println("   a b c d e f g h i j");
        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            System.out.printf("%d  ", row + 1);
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                System.out.print(BOARD[row][col].getSign() + " "); // Cell 에게 Board 를 그려달라는 메시지는 Cell 의 책임 범위를 벗어나기 때문에 getter 를 사용해야한다.
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = Cell.create();
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            BOARD[row][col].turnOnLandMine();
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {

                if (isLandMineCell(row, col)) {
                    continue;
                }
                int count = countNearbyLandMineBasedOn(row, col);
                BOARD[row][col].updateNearbyLandMineCount(count);
            }
        }
    }

    private static int countNearbyLandMineBasedOn(int row, int col) {
        int count = 0;
        if (row - 1 >= 0 && col - 1 >= 0 && isLandMineCell(row - 1, col - 1)) {
            count++;
        }
        if (row - 1 >= 0 && isLandMineCell(row - 1, col)) {
            count++;
        }
        if (row - 1 >= 0 && col + 1 < BOARD_COL_SIZE && isLandMineCell(row - 1, col + 1)) {
            count++;
        }
        if (col - 1 >= 0 && isLandMineCell(row, col - 1)) {
            count++;
        }
        if (col + 1 < BOARD_COL_SIZE && isLandMineCell(row, col + 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col - 1 >= 0 && isLandMineCell(row + 1, col - 1)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && isLandMineCell(row + 1, col)) {
            count++;
        }
        if (row + 1 < BOARD_ROW_SIZE && col + 1 < BOARD_COL_SIZE && isLandMineCell(row + 1, col + 1)) {
            count++;
        }
        return count;
    }

    private static void showGameStartComments() {
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println("지뢰찾기 게임 시작!");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    }

    private static void open(int row, int col) {
        if (row < 0 || row >= BOARD_ROW_SIZE || col < 0 || col >= BOARD_COL_SIZE) {
            return;
        }
        if (BOARD[row][col].isOpened()) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }

        BOARD[row][col].open();

        if (BOARD[row][col].hasNearbyLandMine()) { // getter 를 참아라..
            return;
        }

        open(row - 1, col - 1);
        open(row - 1, col);
        open(row - 1, col + 1);
        open(row, col - 1);
        open(row, col + 1);
        open(row + 1, col - 1);
        open(row + 1, col);
        open(row + 1, col + 1);
    }

}
