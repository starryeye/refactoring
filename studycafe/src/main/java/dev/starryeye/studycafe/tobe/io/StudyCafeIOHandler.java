package dev.starryeye.studycafe.tobe.io;

import dev.starryeye.studycafe.tobe.model.pass.StudyCafePassOrder;
import dev.starryeye.studycafe.tobe.model.pass.locker.StudyCafeLockerPass;
import dev.starryeye.studycafe.tobe.model.pass.seat.StudyCafeSeatPass;
import dev.starryeye.studycafe.tobe.model.pass.StudyCafePassType;
import dev.starryeye.studycafe.tobe.model.pass.seat.StudyCafeSeatPasses;

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

    public void showPassOrderSummary(StudyCafePassOrder passOrder) {
        outputHandler.showPassOrderSummary(passOrder);
    }

    public void showSimpleMessage(String message) {
        outputHandler.showSimpleMessage(message);
    }

    public StudyCafePassType askUserPassType() {
        outputHandler.askPassTypeSelection();
        return inputHandler.getPassTypeSelectingUserAction();
    }

    public StudyCafeSeatPass askUserPassBy(StudyCafeSeatPasses passCandidates) {
        List<StudyCafeSeatPass> passCandidateList = passCandidates.getPasses();
        outputHandler.showPassListForSelection(passCandidateList);
        return inputHandler.getSelectPass(passCandidateList);
    }

    public boolean askUserLockerPassBy(StudyCafeLockerPass lockerPassCandidate) {
        outputHandler.askLockerPass(lockerPassCandidate);
        return inputHandler.getLockerSelection();
    }
}
