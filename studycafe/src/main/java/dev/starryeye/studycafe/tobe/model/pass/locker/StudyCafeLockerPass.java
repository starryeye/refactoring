package dev.starryeye.studycafe.tobe.model.pass.locker;

import dev.starryeye.studycafe.tobe.model.pass.StudyCafePass;
import dev.starryeye.studycafe.tobe.model.pass.StudyCafePassType;

public class StudyCafeLockerPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;

    private StudyCafeLockerPass(StudyCafePassType passType, int duration, int price) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
    }

    public static StudyCafeLockerPass of(StudyCafePassType passType, int duration, int price) {
        return new StudyCafeLockerPass(passType, duration, price);
    }

    public boolean isSameDuration(int duration) {
        return this.duration == duration;
    }

    public boolean isSamePathType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    @Override
    public StudyCafePassType getPassType() {
        return passType;
    }

    @Override
    public int getDuration() {
        return duration;
    }

    @Override
    public int getPrice() {
        return price;
    }

}
