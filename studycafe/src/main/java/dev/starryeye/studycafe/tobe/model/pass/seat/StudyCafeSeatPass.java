package dev.starryeye.studycafe.tobe.model.pass.seat;

import dev.starryeye.studycafe.tobe.model.pass.StudyCafePass;
import dev.starryeye.studycafe.tobe.model.pass.StudyCafePassType;
import dev.starryeye.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;

public class StudyCafeSeatPass implements StudyCafePass {

    private final StudyCafePassType passType;
    private final int duration;
    private final int price;
    private final double discountRate;

    private StudyCafeSeatPass(StudyCafePassType passType, int duration, int price, double discountRate) {
        this.passType = passType;
        this.duration = duration;
        this.price = price;
        this.discountRate = discountRate;
    }

    public static StudyCafeSeatPass of(StudyCafePassType passType, int duration, int price, double discountRate) {
        return new StudyCafeSeatPass(passType, duration, price, discountRate);
    }

    public boolean isSamePathType(StudyCafePassType passType) {
        return this.passType == passType;
    }

    public boolean isSameDurationAndPathType(StudyCafeLockerPass lockerPass) {
        return lockerPass.isSameDuration(this.duration)
                && lockerPass.isSamePathType(this.passType);
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

    public double getDiscountRate() {
        return discountRate;
    }

    public boolean canNotUseLocker() {
        return passType.canNotUseLocker();
    }
}
