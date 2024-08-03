package dev.starryeye.minesweeper.tobe.minesweeper.board.cell;

import java.util.Arrays;
import java.util.List;

public class Cells {

    /**
     * 일급 컬렉션
     *
     * - 해당 객체가 가진 유일한 필드에 관련된 가공 로직 및 비즈니스 로직을
     * 해당 객체에서 가지도록 하여 응집력을 높일 수 있다.
     * - 그냥 컬랙션만 사용하는 것에 비해 비즈니스적인 의미(추상화)를 부여해볼 수도 있다.
     * - 일급 컬렉션 객체의 메서드 반환 타입은 일급 컬렉션 객체 자신이면 좋다. (메서드 체이닝 및 불변성의 이점..) ex. CellPositions
     */

    private final List<Cell> cellList;

    private Cells(List<Cell> cellList) {
        this.cellList = cellList;
    }

    public static Cells of(List<Cell> cells) {
        return new Cells(cells);
    }

    public static Cells from(Cell[][] cells) {
        List<Cell> cellList = Arrays.stream(cells)
                .flatMap(Arrays::stream)
                .toList();
        return of(cellList);
    }

    public boolean isAllChecked() {
        return this.cellList.stream()
                .allMatch(Cell::isChecked); // getter 로 비교하는 것을 참고 객체에게 메시지를 던져서 객체가 스스로 판단하도록 하자
    }
}
