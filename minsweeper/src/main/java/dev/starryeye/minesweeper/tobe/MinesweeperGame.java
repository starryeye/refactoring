package dev.starryeye.minesweeper.tobe;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MinesweeperGame {

    public static final int BOARD_ROW_SIZE = 8;
    public static final int BOARD_COL_SIZE = 10;
    public static final Scanner SCANNER = new Scanner(System.in);
    private static final String[][] BOARD = new String[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final Integer[][] NEARBY_LAND_MINE_COUNTS = new Integer[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    private static final boolean[][] LAND_MINES = new boolean[BOARD_ROW_SIZE][BOARD_COL_SIZE];
    public static final int LAND_MINE_COUNT = 10;
    public static final String FLAG_SIGN = "⚑";
    public static final String LAND_MINE_SIGN = "☼";
    public static final String OPENED_CELL_SIGN = "□";
    public static final String CLOSED_CELL_SIGN = "■";

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
            BOARD[selectedRowIndex][selectedColIndex] = FLAG_SIGN;
            changeGameStatusToWinIfAllCellIsOpened();
            return;
        }

        if (doesUserSelectOpeningTheCell(selectedUserActionInput)) {
            if (isLandMineCell(selectedRowIndex, selectedColIndex)) {
                BOARD[selectedRowIndex][selectedColIndex] = LAND_MINE_SIGN;
                changeGameStatusToLose();
                return;
            }

            open(selectedRowIndex, selectedColIndex);
            changeGameStatusToWinIfAllCellIsOpened();
            return;
        }
        throw new AppException("입력하신 행위, [" + selectedUserActionInput + "] 값은 잘못된 입력입니다.");
    }

    private static void changeGameStatusToLose() {
        gameStatus = -1;
    }

    private static boolean isLandMineCell(int selectedRowIndex, int selectedColIndex) {
        return LAND_MINES[selectedRowIndex][selectedColIndex];
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

    private static void changeGameStatusToWinIfAllCellIsOpened() { // checkGameClearCondition
        if (isAllCellOpened()) {
            changeGameStatusToWin();
        }
    }

    private static void changeGameStatusToWin() {
        gameStatus = 1;
    }

    private static boolean isAllCellOpened() {
        return Arrays.stream(BOARD)
                .flatMap(Arrays::stream)
                .noneMatch(CLOSED_CELL_SIGN::equals); // cell -> cell.equals(CLOSED_CELL_SIGN) 은 cell 값이 null 이면 NPE 이다.. 좀더 안전하게 상수에서 equals 를 호출하여 NPE 발생을 막는다.
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
                System.out.print(BOARD[row][col] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private static void initializeGame() {

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {
                BOARD[row][col] = CLOSED_CELL_SIGN;
            }
        }

        for (int i = 0; i < LAND_MINE_COUNT; i++) {
            int col = new Random().nextInt(BOARD_COL_SIZE);
            int row = new Random().nextInt(BOARD_ROW_SIZE);
            LAND_MINES[row][col] = true;
        }

        for (int row = 0; row < BOARD_ROW_SIZE; row++) {
            for (int col = 0; col < BOARD_COL_SIZE; col++) {

                if (isLandMineCell(row, col)) {
                    NEARBY_LAND_MINE_COUNTS[row][col] = 0;
                    continue;
                }
                NEARBY_LAND_MINE_COUNTS[row][col] = countNearbyLandMineBasedOn(row, col);
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
        if (!BOARD[row][col].equals(CLOSED_CELL_SIGN)) {
            return;
        }
        if (isLandMineCell(row, col)) {
            return;
        }
        if (NEARBY_LAND_MINE_COUNTS[row][col] != 0) {
            BOARD[row][col] = String.valueOf(NEARBY_LAND_MINE_COUNTS[row][col]);
            return;
        } else {
            BOARD[row][col] = OPENED_CELL_SIGN;
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
