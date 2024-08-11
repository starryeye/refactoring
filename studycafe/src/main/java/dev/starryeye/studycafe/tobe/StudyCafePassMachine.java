package dev.starryeye.studycafe.tobe;

import dev.starryeye.studycafe.tobe.exception.AppException;
import dev.starryeye.studycafe.tobe.io.StudyCafeFileHandler;
import dev.starryeye.studycafe.tobe.io.StudyCafeIOHandler;
import dev.starryeye.studycafe.tobe.model.StudyCafeLockerPass;
import dev.starryeye.studycafe.tobe.model.StudyCafePass;
import dev.starryeye.studycafe.tobe.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final StudyCafeIOHandler ioHandler = new StudyCafeIOHandler();
    private final StudyCafeFileHandler studyCafeFileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            ioHandler.showWelcomeMessage();
            ioHandler.showAnnouncement();

            StudyCafePass selectedPass = selectPass();

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

    private StudyCafePass selectPass() {
        StudyCafePassType passType = ioHandler.askUserPassType();

        List<StudyCafePass> passCandidates = findPassCandidatesBy(passType);

        return ioHandler.askUserPassBy(passCandidates);
    }

    private List<StudyCafePass> findPassCandidatesBy(StudyCafePassType studyCafePassType) {
        List<StudyCafePass> allPasses = studyCafeFileHandler.readStudyCafePasses();
        return allPasses.stream()
                .filter(pass -> pass.isSamePathType(studyCafePassType))
                .toList();
    }

    private Optional<StudyCafeLockerPass> selectLockerPass(StudyCafePass selectedPass) {

        if (selectedPass.canNotUseLocker()) {
            return Optional.empty();
        }

        StudyCafeLockerPass lockerPassCandidate = findLockerPassCandidateBy(selectedPass);

        if (lockerPassCandidate != null) {
            boolean isLockerSelected = ioHandler.askUserLockerPassBy(lockerPassCandidate);


            if (isLockerSelected) {
                return Optional.of(lockerPassCandidate);
            }
        }

        return Optional.empty();
    }

    private StudyCafeLockerPass findLockerPassCandidateBy(StudyCafePass pass) {
        List<StudyCafeLockerPass> allLockerPasses = studyCafeFileHandler.readLockerPasses();

        return allLockerPasses.stream()
                .filter(pass::isSameDurationAndPathType)
                .findFirst()
                .orElse(null);
    }

}
