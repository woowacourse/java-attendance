package attendance.io.view;

import java.util.Arrays;

public enum Command {
    ATTENDANCE("1"),
    ATTENDANCE_UPDATE("2"),
    ATTENDANCE_CHECK("3"),
    ATTENDANCE_WARNING_CHECK("4"),
    QUIT("Q");

    private final String value;

    Command(String value) {
        this.value = value;
    }

    public static Command from(String value) {
        return Arrays.stream(values())
                .filter(command -> command.value.equals(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(value + "에 해당하는 커맨드가 존재하지 않습니다."));
    }
}
