package dev.starryeye.studycafe.tobe.model.pass;

import dev.starryeye.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import dev.starryeye.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;

import java.util.Optional;

public class StudyCafePassOrder {

    private final StudyCafeSeatPass seatPass;
    private final StudyCafeLockerPass lockerPass;

    private StudyCafePassOrder(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        this.seatPass = seatPass;
        this.lockerPass = lockerPass;
    }

    public static StudyCafePassOrder of(StudyCafeSeatPass seatPass, StudyCafeLockerPass lockerPass) {
        return new StudyCafePassOrder(seatPass, lockerPass);
    }

    public StudyCafeSeatPass getSeatPass() {
        return this.seatPass;
    }

    public Optional<StudyCafeLockerPass> getLockerPass() {
        return Optional.ofNullable(this.lockerPass);
    }

    public int calculateDiscountPrice() {

        return this.seatPass.calculateDiscountPrice();
    }

    public int calculateTotalPrice() {
        int lockerPassPrice = lockerPass != null ? lockerPass.getPrice() : 0;
        int totalPassPrice = this.seatPass.getPrice() + lockerPassPrice;
        return totalPassPrice - calculateDiscountPrice();
    }
}
