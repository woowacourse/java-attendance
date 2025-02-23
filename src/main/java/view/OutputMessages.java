package view;

import domain.UserInput;

public enum OutputMessages {
    DATE_PROMPT("오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요."),
    FIRST_FUNCTION_PROMPT(UserInput.CHECK_ATTENDANCE.getInput() + " 출석 확인"),
    SECOND_FUNCTION_PROMPT(UserInput.MODIFY_ATTENDANCE.getInput() + " 출석 수정"),
    THIRD_FUNCTION_PROMPT(UserInput.TOTAL_RECORDS_BY_CREW.getInput() + " 크루별 출석 기록 확인"),
    FOURTH_FUNCTION_PROMPT(UserInput.CHECK_PENALTY.getInput() + " 제적 위험자 확인"),
    QUIT_FUNCTION_PROMPT(UserInput.QUIT.getInput() + " 종료"),
    GUIDE_PROMPT("(원하는 기능의 번호 입력)"),

    DISPLAY_CHECK_ATTENDANCE_RESULT("%02d월 %02d일 %s %s %s"),

    DISPLAY_MODIFY_ATTENDANCE_RESULT("%02d월 %02d일 %s %s %s -> %s %s 수정 완료!"),

    DISPLAY_ATTENDANCE_RECORD_PROMPT("이번 달 %s의 출석 기록입니다."),
    DISPLAY_ATTENDANCE_RECORD_RESULT("%02d월 %02d일 %s %s %s"),
    DISPLAY_ATTENDANCE_COUNT("출석: %d회"),
    DISPLAY_LATE_COUNT("지각: %d회"),
    DISPLAY_ABSENT_COUNT("결석: %d회"),
    DISPLAY_HAS_PENALTY("%s 대상자입니다."),

    DISPLAY_PENALTY_PROMPT("제적 위험자 조회 결과"),
    DISPLAY_PENALTY_CREW("- %s: 결석 %d회, 지각 %d회 (%s)");

    private final String format;

    OutputMessages(String format) {
        this.format = format;
    }

    public String getFormat() {
        return format;
    }

    public String format(Object... args) {
        return String.format(format, args);
    }
}
