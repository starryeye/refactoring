package dev.starryeye.studycafe.tobe.model;

import java.util.Set;

public enum StudyCafePassType {

    HOURLY("시간 단위 이용권"),
    WEEKLY("주 단위 이용권"),
    FIXED("1인 고정석");

    private final String description;

    private static final Set<StudyCafePassType> CAN_USE_LOCKER = Set.of(FIXED);

    StudyCafePassType(String description) {
        this.description = description;
    }

    public boolean canUseLocker() {
        return CAN_USE_LOCKER.contains(this);
    }

    public boolean canNotUseLocker() {
        return !canUseLocker();
    }
}
