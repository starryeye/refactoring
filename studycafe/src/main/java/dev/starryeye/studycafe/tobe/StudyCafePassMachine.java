package dev.starryeye.studycafe.tobe;

import dev.starryeye.studycafe.tobe.exception.AppException;
import dev.starryeye.studycafe.tobe.io.StudyCafeIOHandler;
import dev.starryeye.studycafe.tobe.model.pass.*;
import dev.starryeye.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import dev.starryeye.studycafe.tobe.model.pass.locker.StudyCafeLockerPasses;
import dev.starryeye.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;
import dev.starryeye.studycafe.tobe.model.pass.seat.StudyCafeSeatPasses;
import dev.starryeye.studycafe.tobe.provider.StudyCafeLockerPassProvider;
import dev.starryeye.studycafe.tobe.provider.StudyCafeSeatPassProvider;

import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler;
    private final StudyCafeSeatPassProvider seatPassProvider;
    private final StudyCafeLockerPassProvider lockerPassProvider;

    public StudyCafePassMachine(
            StudyCafeIOHandler ioHandler,
            StudyCafeSeatPassProvider studyCafeSeatPassProvider,
            StudyCafeLockerPassProvider studyCafeLockerPassProvider
    ) {
        this.ioHandler = ioHandler;
        this.seatPassProvider = studyCafeSeatPassProvider;
        this.lockerPassProvider = studyCafeLockerPassProvider;
    }

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            StudyCafePassOrder passOrder = StudyCafePassOrder.of(
                    selectedPass, optionalLockerPass.orElse(null)
            );

            ioHandler.showPassOrderSummary(passOrder);

//            optionalLockerPass.ifPresentOrElse(
//                    lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
//                    () -> ioHandler.showPassOrderSummary(selectedPass)
//            );
        } catch (AppException e) {
            ioHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            ioHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeSeatPass selectPass() {
        StudyCafePassType passType = ioHandler.askUserPassType();

        StudyCafeSeatPasses passCandidates = findPassCandidatesBy(passType);

        return ioHandler.askUserPassBy(passCandidates);
    }

    private StudyCafeSeatPasses findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        StudyCafeSeatPasses allPasses = seatPassProvider.getSeatPasses();
        return allPasses.findAllBy(studyCafePassType);
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafeSeatPass selectedPass) {

        if (selectedPass.canNotUseLocker()) {
            return Optional.empty();
        }

        Optional<StudyCafeLockerPass> lockerPassCandidateOptional = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidateOptional.isPresent()) {
            StudyCafeLockerPass lockerPassCandidate = lockerPassCandidateOptional.get();
            boolean isLockerSelected = ioHandler.askUserLockerPassBy(lockerPassCandidate);


            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private Optional<StudyCafeLockerPass> findLockerPassCandidateBy(StudyCafeSeatPass pass) {
        StudyCafeLockerPasses allLockerPasses = lockerPassProvider.getLockerPasses();

        return allLockerPasses.findFirstBy(pass);
    }

}
