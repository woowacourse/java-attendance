package attendance.view.message;

public class OutputMessage {

    public static final String ATTENDANCE_RESULT = "%d월 %2d일 %s %02d:%02d (%s)\n";
    public static final String UPDATE_ATTENDANCE = "%d월 %02d일 %s %02d:%02d (%s) -> %02d:%02d (%s) 수정 완료!\n";
    public static final String CREW_ATTENDANCE_TITLE = "이번 달 %s의 출석 기록입니다.\n";
    public static final String CREW_ATTENDANCE = "%d월 %2d일 %s --:-- (%s)\n";
    public static final String CREW_ATTEND_COUNT = "출석 : %d회\n";
    public static final String CREW_LATE_COUNT = "지각 : %d회\n";
    public static final String CREW_ABSENCE_COUNT = "결석 : %d회\n";
    public static final String WARNING_TO_CREW = "%s 대상자입니다.\n";
    public static final String WARNING_CREWS_TITLE = "제적 위험자 조회 결과";
    public static final String WARNING_CREWS_ATTENDANCE_COUNT = "- %s: 결석 %d회, 지각 %d회 (%s)\n";
}
