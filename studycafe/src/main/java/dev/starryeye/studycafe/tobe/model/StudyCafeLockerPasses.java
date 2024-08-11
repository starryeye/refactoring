package dev.starryeye.studycafe.tobe.model;

import java.util.List;
import java.util.Optional;

public class StudyCafeLockerPasses {

    /**
     * 일급 컬렉션
     */

    private final List<StudyCafeLockerPass> passes;

    private StudyCafeLockerPasses(List<StudyCafeLockerPass> passes) {
        this.passes = passes;
    }

    public static StudyCafeLockerPasses of(List<StudyCafeLockerPass> passes) {
        return new StudyCafeLockerPasses(passes);
    }

    public Optional<StudyCafeLockerPass> findFirstBy(StudyCafePass pass) {
        return passes.stream()
                .filter(pass::isSameDurationAndPathType)
                .findFirst();
    }
}
