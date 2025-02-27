package view;

public class ViewMessage {
    public static final String SELECT_MENU_INTRO = "오늘은 %02d월 %02d일 %s입니다. 기능을 선택해 주세요.%n";
    public static final String SELECT_MENU = """
            1. 출석 확인
            2. 출석 수정
            3. 크루별 출석 기록 확인
            4. 제적 위험자 확인
            Q. 종료
            """;
    public static final String NICKNAME = "닉네임을 입력해 주세요.";
    public static final String ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";

    public static final String UPDATE_NICKNAME = "출석을 수정하려는 크루의 " + NICKNAME;
    public static final String UPDATE_DATE = "수정하려는 날짜(일)를 입력해 주세요.";
    public static final String UPDATE_WHEN = "언제로 변경하겠습니까?";

    public static final String CURRENT_MONTH_ATTENDANCE_SHEET = "이번 달 %s의 출석 기록입니다.%n";
    public static final String ATTENDANCE_FORMAT = "12월 %02d일 %s %s (%s)%n";
    public static final String TIME_FORMAT = "%02d:%02d";

    public static final String STATISTICS_FORMAT = """
            출석: %d회
            지각: %d회
            결석: %d회""";
    public static final String ABSENT_POLICY_FORMAT = "%s 대상자입니다%n";
    public static final String RISK_OF_EXPULSION_BANNER = "제적 위험자 조회 결과";
    public static final String RISK_OF_EXPULSION_FORMAT = "- %s: 결석 %d회, 지각 %d회 (%s)%n";

}