package domain;

import java.util.Arrays;

public enum MenuOption {

    ATTENDANCE_CHECK("1", "출석 확인"),
    ATTENDANCE_CORRECTION("2", "출석 수정"),
    CREW_ATTENDANCE_CHECK("3", "크루별 출석 기록 확인"),
    CHECK_EXPELLED_CREW("4", "제적 위험자 확인"),
    QUIT("Q", "종료");

    private final String command;
    private final String option;

    MenuOption(String command, String option) {
        this.command = command;
        this.option = option;
    }

    public static void validateCommandExist(String command) {
        boolean optionMatch = Arrays.stream(MenuOption.values())
                .anyMatch(menuOption -> menuOption.getCommand().equals(command));

        if (!optionMatch) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 옵션입니다.");
        }
    }

    public String getCommand() {
        return this.command;
    }

    public String getOption() {
        return this.option;
    }
}
