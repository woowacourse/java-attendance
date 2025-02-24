package domain;

import static controller.AttendanceController.processAttendanceRecordByCrew;
import static controller.AttendanceController.processEditAttendance;
import static view.OutputView.printAllExpulsion;

import controller.AttendanceController;
import exception.CommandException;
import java.util.Arrays;
import java.util.function.BiConsumer;

public enum Command {

    CHECK_ATTENDEES("출석 확인", "1", AttendanceController::processCheckAttendees),
    EDIT_ATTENDANCE("출석 수정", "2", (crews, attendanceDateTime) -> processEditAttendance(crews)),
    CHECK_THE_ATTENDANCE_RECORD_BY_CREW("크루별 출석 기록 확인", "3",
            (crews, attendanceDateTime) -> processAttendanceRecordByCrew(crews)),
    CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION("제적 위험자 확인", "4",
            (crews, attendanceDateTime) -> printAllExpulsion(crews)),
    QUIT("종료", "Q", (crews, fixDateTime) -> {
    });

    private final String commandName;
    private final String commandNumber;
    private final BiConsumer<Crews, AttendanceDateTime> action;

    Command(final String commandName, final String commandNumber, final BiConsumer<Crews, AttendanceDateTime> action) {
        this.commandName = commandName;
        this.commandNumber = commandNumber;
        this.action = action;
    }

    public static Command findByCommandNumber(final String commandNumber) {
        return Arrays.stream(Command.values())
                .filter(c -> c.commandNumber.equals(commandNumber))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(CommandException.INVALID_FORMAT.getMessage()));
    }

    public void execute(final Crews crews, final AttendanceDateTime attendanceDateTime) {
        action.accept(crews, attendanceDateTime);
    }

    public String getCommandName() {
        return commandName;
    }

    public String getCommandNumber() {
        return commandNumber;
    }
}
