package attendance.model;

import java.util.Arrays;
import java.util.List;

public enum Command {

    ATTENDANCE("1", "출석 확인"),
    EDIT_ATTENDANCE("2", "출석 수정"),
    ATTENDANCE_LOGS("3", "크루별 출석 기록 확인"),
    WARNING_LIST("4", "제적 위험자 확인"),
    QUIT("Q", "종료");

    private final String code;
    private final String description;

    Command(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public boolean isMatch(String code) {
        return this.code.equals(code);
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static List<Command> getCommands() {
        return Arrays.stream(values())
                .toList();
    }

    public static Command from(String input) {
        return Arrays.stream(values())
                .filter(command -> command.isMatch(input))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 커맨드입니다. 입력: %s".formatted(input)));
    }
}
