package dev.starryeye.minesweeper.tobe.minesweeper.io.sign;

import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshot;
import dev.starryeye.minesweeper.tobe.minesweeper.board.cell.CellSnapshotStatus;

import java.util.Arrays;

public enum CellSignFinder implements CellSignProvider {

    /**
     * 해당 Enum 은 ConsoleCellSign 과 유사하지만..
     * CellSignProvider 인터페이스를 만들고 이를 구현함으로써
     * template 과 callback 이 분리된 형태는 아니지만 조금만 수정하면 해볼 수 있다.
     * -> CellSignFinder 자체가 callback 이 될 수 있고, findCellSignBy static 메서드가 template 이 될 수 있다.
     *
     * template 과 callback 을 분리하면, CellSignTemplate 을 사용하는 방법에 비해
     * callback 이 CellSignProvider 의 여러 구현체 리스트로 관리 될 필요 없이 해당 enum 만 관리하면 될 것이다.
     */

    UNCHECKED(CellSnapshotStatus.UNCHECKED) {
        @Override
        public String provideBy(CellSnapshot cellSnapshot) {
            return UNCHECKED_SIGN;
        }
    },
    EMPTY(CellSnapshotStatus.EMPTY) {
        @Override
        public String provideBy(CellSnapshot cellSnapshot) {
            return EMPTY_SIGN;
        }
    },
    FLAG(CellSnapshotStatus.FLAG) {
        @Override
        public String provideBy(CellSnapshot cellSnapshot) {
            return FLAG_SIGN;
        }
    },
    NUMBER(CellSnapshotStatus.NUMBER) { // 인터페이스로 추상화 하고 인터페이스를 여기에서 구현함으로써 if 문을 제거할 수 있었다. (ConsoleCellSign 의 sign(int) 와 비교

        @Override
        public String provideBy(CellSnapshot cellSnapshot) {
            return String.valueOf(cellSnapshot.getNearbyLandMineCount());
        }
    },
    LAND_MINE(CellSnapshotStatus.LAND_MINE) {
        @Override
        public String provideBy(CellSnapshot cellSnapshot) {
            return LAND_MINE_SIGN;
        }
    },
    ;

    private static final String UNCHECKED_SIGN = "■";
    private static final String EMPTY_SIGN = "□";
    private static final String FLAG_SIGN = "⚑";
    private static final String LAND_MINE_SIGN = "☼";

    private final CellSnapshotStatus status;

    CellSignFinder(CellSnapshotStatus status) {
        this.status = status;
    }

    @Override
    public boolean supports(CellSnapshot cellSnapshot) {
        return cellSnapshot.isSameStatus(this.status);
    }

    // 예비 template
    public static String findCellSignBy(CellSnapshot cellSnapshot) {
        return Arrays.stream(values())
                .filter(cellSignFinder -> cellSignFinder.supports(cellSnapshot))
                .findFirst()
                .map(cellSignFinder -> cellSignFinder.provideBy(cellSnapshot))
                .orElseThrow(() -> new IllegalArgumentException("확인할 수 없는 셀입니다."));
    }
}
