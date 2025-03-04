package attendance.view;

public class OutputMessage {
    public static final String CONFIRM_RESULT_TILE = "%02d월 %02d일 %s %02d:%02d (%s)\n";

    public static final String UPDATE_RESULT_TITLE = "%02d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n";

    public static final String CREW_ATTENDANCE_TITLE = "이번 달 %s의 출석 기록입니다.\n";
    public static final String ATTEND_COUNT = "출석: %d회\n";
    public static final String LATE_COUNT = "지각: %d회\n";
    public static final String ABSENCE_COUNT = "결석: %d회\n";
    public static final String PENALTY_WARNING = "%s 대상자입니다.\n";

    public static final String PENALTY_CREWS_TITLE = "제적 위험자 조회 결과";

    public static final String ATTEND = "춣석";
    public static final String LATE = "지각";
    public static final String ABSENCE = "결석";
    public static final String BLANK = "";

    public static final String NOT_VISIT_ABSENCE = "%02d월 %02d일 %s --:-- (결석)\n";
    public static final String LATE_ABSENCE = "%02d월 %02d일 %s %02d:%02d (%s)\n";

    public static final String EXPULSION = "제적";
    public static final String INTERVIEW = "면담";
    public static final String WARNING = "경고";

    public static final String CREWS_PER_PENALTY = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
}
