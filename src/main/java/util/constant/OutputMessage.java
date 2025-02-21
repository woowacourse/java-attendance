package util.constant;

public class OutputMessage {

    public static final String ATTENDANCE_RECORD_FORMAT = "%s %s%n";
    public static final String ABSENCE_RECORD_FORMAT = "--:-- (결석)";
    public static final String ATTENDANCE_EDIT_FORMAT = "%s %s -> %s 수정 완료!%n";
    public static final String DATE_PRINT_FORMAT = "%02d월 %02d일 %s";
    public static final String TIME_PRINT_FORMAT = "%02d:%02d (%s)";

    public static final String CREW_ATTENDANCE_LIST_MESSAGE = "이번 달 %s의 출석 기록입니다.%n";
    public static final String TOTAL_ATTEND_FORMAT = "출석: %s회%n";
    public static final String TOTAL_LATENESS_FORMAT = "지각: %s회%n";
    public static final String TOTAL_ABSENCE_FORMAT = "결석: %s회%n";
    public static final String PENALTY_FORMAT = "%s 대상자입니다.%n";

    public static final String WARNING_CREW_LIST_MESSAGE = "제적 위험자 조회 결과";
    public static final String WARNING_CREW_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)%n";
}
