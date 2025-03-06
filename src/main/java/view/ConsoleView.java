package view;

import controller.dto.AfterAttendanceInfo;
import controller.dto.BeforeAttendanceInfo;
import controller.dto.AttendanceResult;
import controller.dto.CrewAttendanceRecord;
import domain.AttendanceStatus;
import domain.vo.PenaltyTarget;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import view.parser.InputParser;
import view.parser.OutputParser;

public final class ConsoleView {

    private static ConsoleView consoleView;

    private final InputView inputView;
    private final OutputView outputView;
    private final InputParser inputParser;
    private final OutputParser outputParser;

    public ConsoleView(InputView inputView, OutputView outputView, InputParser inputParser, OutputParser outputParser) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputParser = inputParser;
        this.outputParser = outputParser;
    }

    public Menu requestMenu(LocalDate applicationDate) {
        String inputMenuSelector = inputView.inputMenuSelector(applicationDate);
        return Menu.findBySelector(inputMenuSelector);
    }

    public void printMessage(String message) {
        outputView.printMessage(message);
    }

    public String requestNickName() {
        return inputView.inputNickName();
    }

    public String requestNickNameForUpdate() {
        return inputView.inputNickNameForUpdate();
    }

    public LocalTime requestAttendanceTime() {
        String rawInputAttendanceTime = inputView.inputAttendanceTime();
        return inputParser.parseToLocalTime(rawInputAttendanceTime);
    }

    public void printAttendanceResult(AttendanceResult attendanceResult) {
        LocalDate date = attendanceResult.attendanceDate();
        LocalTime time = attendanceResult.attendanceTime();
        AttendanceStatus attendanceStatus = attendanceResult.attendanceStatus();

        outputView.printAttendanceResult(date, inputParser.parseToTimeMessage(time), outputParser.parseAttendanceStatusToMessage(attendanceStatus));
    }

    public LocalTime requestUpdateTime() {
        String rawInputUpdateTime = inputView.inputUpdateTime();
        return inputParser.parseToLocalTime(rawInputUpdateTime);
    }

    public int requestDayOfMonth() {
        return inputParser.parseToInt(inputView.inputDayOfMonth());
    }

    public void printUpdateResult(BeforeAttendanceInfo beforeAttendanceInfo, AfterAttendanceInfo afterAttendanceInfo) {
        LocalDate date = beforeAttendanceInfo.updateDate();
        String beforeTime = inputParser.parseToTimeMessage(beforeAttendanceInfo.beforeTime());
        String beforeAttendanceStatus = outputParser.parseAttendanceStatusToMessage(beforeAttendanceInfo.attendanceStatus());
        String afterTime = inputParser.parseToTimeMessage(afterAttendanceInfo.updateTime());
        String afterAttendanceStatus = outputParser.parseAttendanceStatusToMessage(afterAttendanceInfo.attendanceStatus());
        outputView.printUpdateResult(date, beforeTime, beforeAttendanceStatus, afterTime, afterAttendanceStatus);
    }

    public void printCrewAttendanceRecord(CrewAttendanceRecord record) {
        outputView.printIntroAttendanceRecord();
        record.attendanceResults().forEach(this::printAttendanceResult);
        outputView.printAttendancesCount(record.attendanceCount(), record.lateCount(), record.absenceCount());
        outputView.printPenaltyStatus(outputParser.parsePenaltyToMessage(record.crewPenaltyPolicy()));
    }

    public void printPenaltyTargets(List<PenaltyTarget> penaltyTargets) {
        outputView.printPenaltyResultMessage();
        penaltyTargets
                .forEach(penaltyTarget -> outputView.printPenaltyTarget(
                        penaltyTarget.nickName(),
                        penaltyTarget.lateCount(),
                        penaltyTarget.absenceCount(),
                        outputParser.parsePenaltyToMessage(penaltyTarget.crewPenaltyPolicy())
                ));
    }

    public static ConsoleView create(InputView inputView, OutputView outputView, InputParser inputParser, OutputParser outputParser) {
        if (consoleView == null) {
            consoleView = new ConsoleView(inputView, outputView, inputParser, outputParser);
            return consoleView;
        }
        return consoleView;
    }
}
