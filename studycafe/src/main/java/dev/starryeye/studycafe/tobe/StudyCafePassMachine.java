package dev.starryeye.studycafe.tobe;

import dev.starryeye.studycafe.tobe.exception.AppException;
import dev.starryeye.studycafe.tobe.io.StudyCafeFileHandler;
import dev.starryeye.studycafe.tobe.io.StudyCafeIOHandler;
import dev.starryeye.studycafe.tobe.model.*;

import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafeSeatPass selectedPass = selectPass();

            Optional<StudyCafeLockerPass> optionalLockerPass = selectLockerPass(selectedPass);

            optionalLockerPass.ifPresentOrElse(
                    lockerPass -> ioHandler.showPassOrderSummary(selectedPass, lockerPass),
                    () -> ioHandler.showPassOrderSummary(selectedPass)
            );
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
        StudyCafeSeatPasses allPasses = studyCafeFileHandler.readStudyCafePasses();
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
        StudyCafeLockerPasses allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.findFirstBy(pass);
    }

}
