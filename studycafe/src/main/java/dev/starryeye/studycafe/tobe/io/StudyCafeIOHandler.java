package dev.starryeye.studycafe.tobe.io;

import dev.starryeye.studycafe.tobe.model.StudyCafeLockerPass;
import dev.starryeye.studycafe.tobe.model.StudyCafePass;
import dev.starryeye.studycafe.tobe.model.StudyCafePassType;

import java.util.List;

public class StudyCafeIOHandler {

    /**
     * 로직 상, inputHandler 와 outputHandler 는 따로따로 사용되지 않고 같이 사용되는 경우가 많아서
     * IOHandler 로 둘을 묶은 객체로 추상화 하였다.
     */

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void showWelcomeMessage() {
        outputHandler.showWelcomeMessage();
    }

    public void showAnnouncement() {
        outputHandler.showAnnouncement();
    }

    public void showPassOrderSummary(StudyCafePass selectedPass) {
        outputHandler.showPassOrderSummary(selectedPass);
    }

    public void showPassOrderSummary(StudyCafePass selectedPass, StudyCafeLockerPass lockerPass) {
        outputHandler.showPassOrderSummary(selectedPass, lockerPass);
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public StudyCafePassType askUserPassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public StudyCafePass askUserPassBy(List<StudyCafePass> passCandidates) {
        outputHandler.showPassListForSelection(passCandidates);
        return inputHandler.getSelectPass(passCandidates);
    }

    public boolean askUserLockerPassBy(StudyCafeLockerPass lockerPassCandidate) {
        outputHandler.askLockerPass(lockerPassCandidate);
        return inputHandler.getLockerSelection();
    }
}
