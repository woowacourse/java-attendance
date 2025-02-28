package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import attendance.utility.DateTimeFormatterWrapper;

public class OutputView {

    private final static String METHOD_MESSAGE = """
        1. 출석 확인
        2. 출석 수정
        3. 크루별 출석 기록 확인
        4. 제적 위험자 확인
        Q. 종료""";
    private final static String REQUEST_METHOD = "\n오늘은 MM월 d일 E요일입니다. 기능을 선택해 주세요.";
    private final static String REQUEST_NICKNAME = "닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_TIME = "등교 시간을 입력해 주세요.";
    private final static String REQUEST_ATTENDANCE_MODIFY_DATE = "수정하려는 날짜(일)를 입력해 주세요.";

    private static final String ATTENDANCE_FORMAT = "\nMM월 dd일 E요일 HH:mm (%s)";
    private static final String FORMAT_ATTENDANCE_NOT_EXISTING = "\nMM월 dd일 E요일 --:-- (결석) ";
    private static final String MODIFY_TIME_FORM = "-> HH:mm (%s)";

    private static final String INITIALIZE_HISTORY = "\n이번 달 %s의 출석 기록입니다.";
    private static final String FORMAT_STATE = "\n%s: %d회";
    private static final String SANCTION_LEVEL = "\n%s 대상자입니다.\n";

    private static final String INITIALIZE_SANCTION_STATISTIC = "\n제적 위험자 조회 결과";
    private static final String FORMAT_SANCTION = "\n- %s: 결석 %d회, 지각 %d회 (%s)";

    private static final StringBuilder stringBuilder = new StringBuilder();

    public void println(String string) {
        System.out.println(string);
    }

    public void printStringBuilder() {
        System.out.println(stringBuilder.toString());
        stringBuilder.setLength(0);
    }

    public void printError(String message) {
        println(message);
    }

    public void printRequestMessage(LocalDateTime date) {
        String message = DateTimeFormatterWrapper.getFormatter(REQUEST_METHOD).format(date);
        println(message);
    }

    public void printMethod() {
        println(METHOD_MESSAGE);
    }

    public void printRequestNickname() {
        println(REQUEST_NICKNAME);
    }

    public void printRequestNicknameForModify() {
        println(REQUEST_ATTENDANCE_MODIFY_NICKNAME);
    }

    public void printRequestTime() {
        println(REQUEST_ATTENDANCE_TIME);
    }

    public void printRequestDate() {
        println(REQUEST_ATTENDANCE_MODIFY_DATE);
    }

    public void printlnRegister(String status, LocalDateTime dateTime) {
        String format = String.format(ATTENDANCE_FORMAT, status);
        String formattedMessage = DateTimeFormatterWrapper.getFormatter(format).format(dateTime);

        println(formattedMessage);
    }

    public void appendModifiedOldAttendance(String status, LocalDateTime dateTime) {
        String formatOld = String.format(ATTENDANCE_FORMAT, status);
        String formattedMessageOld = DateTimeFormatterWrapper.getFormatter(formatOld).format(dateTime);

        stringBuilder.append(formattedMessageOld);
    }

    public void appendModifiedAbsence(LocalDateTime dateTime) {
        String formattedMessageOld = DateTimeFormatterWrapper.getFormatter(FORMAT_ATTENDANCE_NOT_EXISTING)
            .format(dateTime);

        stringBuilder.append(formattedMessageOld);
    }

    public void printlnModify(String status, LocalTime time) {
        String formatNew = String.format(MODIFY_TIME_FORM, status);
        String formattedMessageNew = DateTimeFormatterWrapper.getFormatter(formatNew).format(time);

        stringBuilder.append(formattedMessageNew);
        printStringBuilder();
    }

    public void printlnInitializeHistory(String nickname) {
        println(String.format(INITIALIZE_HISTORY, nickname));
    }

    public void appendAttendance(LocalDateTime dateTime, String status) {
        String format = String.format(ATTENDANCE_FORMAT, status);
        String formattedMessage = DateTimeFormatterWrapper.getFormatter(format).format(dateTime);
        stringBuilder.append(formattedMessage);
    }

    public void appendAbsence(LocalDate date) {
        String formattedMessage = DateTimeFormatterWrapper.getFormatter(FORMAT_ATTENDANCE_NOT_EXISTING).format(date);
        stringBuilder.append(formattedMessage);
    }

    public void appendStatisticToBuilder(String status, int count) {
        String formattedMessage = String.format(FORMAT_STATE, status, count);
        stringBuilder.append(formattedMessage);
    }

    public void printJudgedSanctionLevel(String sanctionLevel) {
        println(String.format(SANCTION_LEVEL, sanctionLevel));
    }

    public void printlnInitializeSanctionStatistic() {
        println(INITIALIZE_SANCTION_STATISTIC);
    }

    public void appendSanctionStatistic(String nickname, int absenceCount, int lateCount, String sanctionLevel) {
        String formattedMessage = String.format(FORMAT_SANCTION, nickname, absenceCount, lateCount, sanctionLevel);
        stringBuilder.append(formattedMessage);
    }

}
