package dev.starryeye.studycafe.tobe;

import dev.starryeye.studycafe.tobe.io.LockerPassFileReader;
import dev.starryeye.studycafe.tobe.io.SeatPassFileReader;
import dev.starryeye.studycafe.tobe.io.StudyCafeIOHandler;
import dev.starryeye.studycafe.tobe.provider.StudyCafeLockerPassProvider;
import dev.starryeye.studycafe.tobe.provider.StudyCafeSeatPassProvider;

public class StudyCafeApplication {

    public static void main(String[] args) {

        StudyCafeIOHandler studyCafeIOHandler = new StudyCafeIOHandler();
        StudyCafeSeatPassProvider studyCafeSeatPassProvider = new SeatPassFileReader();
        StudyCafeLockerPassProvider studyCafeLockerPassProvider = new LockerPassFileReader();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(
                studyCafeIOHandler,
                studyCafeSeatPassProvider,
                studyCafeLockerPassProvider
        );

        studyCafePassMachine.run();
    }

}
