package dev.starryeye.studycafe.custom;

import dev.starryeye.studycafe.custom.exception.AppException;
import dev.starryeye.studycafe.custom.io.InputHandler;
import dev.starryeye.studycafe.custom.io.OutputHandler;
import dev.starryeye.studycafe.custom.io.StudyCafeFileHandler;
import dev.starryeye.studycafe.custom.model.StudyCafeLockerPass;
import dev.starryeye.studycafe.custom.model.StudyCafePass;
import dev.starryeye.studycafe.custom.model.StudyCafePassType;

import java.util.List;
import java.util.Optional;

public class StudyCafePassMachine {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    private final StudyCafeFileHandler fileHandler = new StudyCafeFileHandler();

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType selectedPassType = inputHandler.getPassTypeSelectingUserAction();
            List<StudyCafePass> selectedPasses = getSelectedPassesBy(selectedPassType);
            outputHandler.showPassListForSelection(selectedPasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(selectedPasses);

            if (selectedPassType == StudyCafePassType.HOURLY || selectedPassType == StudyCafePassType.WEEKLY) {

                outputHandler.showPassOrderSummary(selectedPass);
            } else if (selectedPassType == StudyCafePassType.FIXED) {

                Optional<StudyCafeLockerPass> lockerPass = getLockerPass(selectedPass);

                if (lockerPass.isEmpty()) {
                    outputHandler.showPassOrderSummary(selectedPass);
                    return;
                }

                outputHandler.askLockerPass(lockerPass.get());
                boolean lockerSelection = inputHandler.getLockerSelection();

                if (lockerSelection) {
                    outputHandler.showPassOrderSummaryWithLockerPass(selectedPass, lockerPass.get());
                } else {
                    outputHandler.showPassOrderSummary(selectedPass);
                }
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private Optional<StudyCafeLockerPass> getLockerPass(StudyCafePass selectedPass) {

        List<StudyCafeLockerPass> lockerPasses = getAllLockerPasses();

        return lockerPasses.stream()
                .filter(lockerPass -> lockerPass.equalsPassTypeAndDuration(selectedPass.getPassType(), selectedPass.getDuration()))
                .findFirst();
    }

    private List<StudyCafeLockerPass> getAllLockerPasses() {
        return fileHandler.readLockerPasses();
    }

    private List<StudyCafePass> getSelectedPassesBy(StudyCafePassType studyCafePassType) {

        List<StudyCafePass> allPasses = getAllPasses();

        return allPasses.stream()
                .filter(studyCafePass -> studyCafePass.equalsPassType(studyCafePassType))
                .toList();
    }

    private List<StudyCafePass> getAllPasses() {
        return fileHandler.readStudyCafePasses();
    }


}
