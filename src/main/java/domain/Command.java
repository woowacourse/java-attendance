package domain;

import controller.AttendanceCommand;
import controller.command.AttendanceRecordByCrewCommand;
import controller.command.CheckAttendanceCommand;
import controller.command.ConfirmationOfThoseAtRiskOfExpulsion;
import controller.command.EditAttendanceCommand;
import error.CustomIllegalArgumentException;
import java.util.Arrays;

public enum Command {

    CHECK_ATTENDEES("출석 확인", "1", CheckAttendanceCommand::new),
    EDIT_ATTENDANCE("출석 수정", "2", EditAttendanceCommand::new),
    CHECK_THE_ATTENDANCE_RECORD_BY_CREW("크루별 출석 기록 확인", "3", AttendanceRecordByCrewCommand::new),
    CONFIRMATION_OF_THOSE_AT_RISK_OF_EXPULSION("제적 위험자 확인", "4", ConfirmationOfThoseAtRiskOfExpulsion::new),
    QUIT("종료", "Q", null);

    private String commandName;
    private String commandNumber;
    private final CommandMapper mapper;

    Command(final String commandName, final String commandNumber, final CommandMapper mapper) {
        this.commandName = commandName;
        this.commandNumber = commandNumber;
        this.mapper = mapper;
    }


    public static Command findByCommandNumber(final String commandNumber) {
        return Arrays.stream(Command.values())
                .filter(c -> c.commandNumber.equals(commandNumber))
                .findFirst()
                .orElseThrow(() -> new CustomIllegalArgumentException("알맞은 명령어를 입력하세요."));
    }

    public String getCommandNumber() {
        return commandNumber;
    }

    public AttendanceCommand getCommandInstance() {
        if (mapper == null) {
            return null;
        }
        return mapper.apply();
    }

    public String getCommandName() {
        return commandName;
    }
    
    @FunctionalInterface
    public interface CommandMapper {
        AttendanceCommand apply();
    }
}
