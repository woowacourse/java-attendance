package view;

import except.AttendanceException;
import java.util.Arrays;

public enum InputMethod {
    ATTENDANCE("1"),
    MODIFY("2"),
    ATTENDANCE_HISTORY("3"),
    DISMISS_HISTORY("4"),
    QUIT("Q"),
    ;

    private static final String INVALID_SELECTED_METHOD = "유효하지 않은 기능 선택입니다.";
    String method;

    InputMethod(String method) {
        this.method = method;
    }

    public static InputMethod from(String inputMethod) {
        InputMethod[] inputMethods = InputMethod.values();
        return Arrays.stream(inputMethods)
                .filter((attendanceMethod) -> attendanceMethod.method.equals(inputMethod))
                .findAny()
                .orElseThrow(() -> new AttendanceException(INVALID_SELECTED_METHOD));
    }
}
