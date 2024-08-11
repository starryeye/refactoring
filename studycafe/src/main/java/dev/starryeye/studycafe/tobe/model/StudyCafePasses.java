package dev.starryeye.studycafe.tobe.model;

import java.util.ArrayList;
import java.util.List;

public class StudyCafePasses {

    /**
     * 일급 컬렉션
     */

    private final List<StudyCafePass> passes;

    private StudyCafePasses(List<StudyCafePass> passes) {
        this.passes = passes;
    }

    public static StudyCafePasses of(List<StudyCafePass> passes) {
        return new StudyCafePasses(passes);
    }

    public StudyCafePasses findAllBy(StudyCafePassType passType) {
        return of(
                passes.stream()
                .filter(pass -> pass.isSamePathType(passType))
                .toList()
        );
    }

    public List<StudyCafePass> getPasses() {
        return new ArrayList<>(this.passes);
    }
}
