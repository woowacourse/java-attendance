package attendance.model;

import java.util.Arrays;

public enum Command {

    ATTENDANCE("1"),
    ATTENDANCE_UPDATE("2"),
    ATTENDANCE_TIMELINE("3"),
    EMERGENCY_CHECK("4"),
    QUIT("Q"),
    ;

    private String value;

    Command(String value) {
        this.value = value;
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(input.trim()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("해당 커맨드가 없습니다."));
    }
}
