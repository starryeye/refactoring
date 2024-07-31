package dev.starryeye.minesweeper.tobe.position;

import dev.starryeye.minesweeper.tobe.cell.Cell;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CellPositions {

    private final List<CellPosition> positions;

    private CellPositions(List<CellPosition> positions) {
        this.positions = positions;
    }

    public static CellPositions of(List<CellPosition> cellPositions) {
        return new CellPositions(cellPositions);
    }

    public static CellPositions from(Cell[][] cells) {

        List<CellPosition> cellPositions = new ArrayList<>();

        for (int row = 0; row < cells.length; row++) {
            for (int col = 0; col < cells[0].length; col++) {
                CellPosition cellPosition = CellPosition.of(row, col);
                cellPositions.add(cellPosition);
            }
        }
        return of(cellPositions);
    }

    public CellPositions extractRandomCellPositions(int count) {

        ArrayList<CellPosition> cellPositions = new ArrayList<>(this.positions); // 원본에 영향이 가지 않도록

        Collections.shuffle(cellPositions);
        return CellPositions.of(cellPositions.subList(0, count));
    }

    public CellPositions subtract(CellPositions subtrahend) {

        ArrayList<CellPosition> minuend = new ArrayList<>(this.positions);

        List<CellPosition> result = minuend.stream()
                .filter(subtrahend::isNotContained)
                .toList();
        return CellPositions.of(result);
    }

    public List<CellPosition> getAll() {
        return new ArrayList<>(this.positions); // 일급 컬렉션 필드를 반환할 때는 외부에서 참조할 수 없도록 새로 만들어 반환해준다.
    }


    private boolean isNotContained(CellPosition cellPosition) {
        return !positions.contains(cellPosition);
    }
}
