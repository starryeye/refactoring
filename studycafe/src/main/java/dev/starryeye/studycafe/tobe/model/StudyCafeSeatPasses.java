package dev.starryeye.studycafe.tobe.model;

import java.util.ArrayList;
import java.util.List;

public class StudyCafeSeatPasses {

    /**
     * 일급 컬렉션
     */

    private final List<StudyCafeSeatPass> passes;

    private StudyCafeSeatPasses(List<StudyCafeSeatPass> passes) {
        this.passes = passes;
    }

    public static StudyCafeSeatPasses of(List<StudyCafeSeatPass> passes) {
        return new StudyCafeSeatPasses(passes);
    }

    public StudyCafeSeatPasses findAllBy(StudyCafePassType passType) {
        return of(
                passes.stream()
                .filter(pass -> pass.isSamePathType(passType))
                .toList()
        );
    }

    public List<StudyCafeSeatPass> getPasses() {
        return new ArrayList<>(this.passes);
    }
}
