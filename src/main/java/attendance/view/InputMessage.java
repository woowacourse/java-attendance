package attendance.view;

public class InputMessage {
    public static final String SELECT_COMMAND_TITLE = "오늘은 %d월 %d일 %s입니다. 기능을 선택해 주세요.\n";
    public static final String SELECT_COMMAND_MENU = "1. 출석 확인\n" +
            "2. 출석 수정\n" +
            "3. 크루별 출석 기록 확인\n" +
            "4. 제적 위험자 확인\n" +
            "Q. 종료";
    public static final String INPUT_NICKNAME = "닉네임을 입력해 주세요.";
    public static final String INPUT_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    public static final String TIME_FORMAT_EXCEPTION = "시간 형식으로 입력해주세요.";
    public static final String INPUT_UPDATE_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    public static final String INPUT_UPDATE_DATE = "수정하려는 날짜(일)를 입력해 주세요.";
    public static final String INTEGER_EXCEPTION = "정수로 입력해주세요.";
    public static final String INPUT_UPDATE_ATTENDANCE_TIME = "언제로 변경하겠습니까?";
}
