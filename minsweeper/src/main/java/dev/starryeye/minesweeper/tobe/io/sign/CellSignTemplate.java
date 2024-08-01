package dev.starryeye.minesweeper.tobe.io.sign;

import dev.starryeye.minesweeper.tobe.cell.CellSnapshot;

import java.util.List;

public class CellSignTemplate {


    private final List<CellSignProvider> cellSignProviders;

    public CellSignTemplate() {

        // default callback
        this.cellSignProviders = List.of(
                new UncheckedCellSignProvider(),
                new EmptyCellSignProvider(),
                new FlagCellSignProvider(),
                new NumberCellSignProvider(),
                new LandMineCellSignProvider()
        );
    }

    public CellSignTemplate(List<CellSignProvider> cellSignProviders) {

        // template 이 생성될 때 default callback 을 외부에서 주입되도록 한다.
        this.cellSignProviders = cellSignProviders;
    }

    // default callback 을 활용하는 편의 메서드
    public String findCellSignBy(CellSnapshot cellSnapshot) {
        return findCellSignBy(cellSnapshot, this.cellSignProviders);
    }

    // 런타임에 외부에서 주입해준 callback 을 사용하는 template
    public String findCellSignBy(CellSnapshot cellSnapshot, List<CellSignProvider> cellSignProviders) {
        // 다형성을 이용하여 if 문이나 switch 문을 없앨 수 있었다. (for 문을 사용하게 됨)
        return cellSignProviders.stream()
                .filter(provider -> provider.supports(cellSnapshot))
                .findFirst()
                .map(provider -> provider.provideBy(cellSnapshot))
                .orElseThrow(() -> new IllegalArgumentException("확인 할 수 없는 셀입니다."));
    }
}
