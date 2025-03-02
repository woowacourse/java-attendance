package attendance.view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
    private final static String REQUEST_ATTENDANCE_MODIFY_TIME = "언제로 변경하겠습니까?";

    private static final String ATTENDANCE_FORMAT = "\nMM월 dd일 E요일 HH:mm (%s)";
    private static final String FORMAT_ATTENDANCE_NOT_EXISTING = "\nMM월 dd일 E요일 --:-- (결석) ";
    private static final String MODIFY_TIME_FORM = "-> HH:mm (%s)";

    private static final String INITIALIZE_HISTORY = "\n이번 달 %s의 출석 기록입니다.\n";
    private static final String FORMAT_STATE = """
        출석: %d회
        지각: %d회
        결석: %d회
        """;
    private static final String SANCTION_LEVEL = "\n%s 대상자입니다.";

    private static final String INITIALIZE_SANCTION_STATISTIC = "\n제적 위험자 조회 결과";
    private static final String FORMAT_SANCTION = "\n- %s: 결석 %d회, 지각 %d회 (%s)";

    private final static StringBuilder stringBuilder = new StringBuilder();

    public void print(String message) {
        System.out.println(message);
    }

    public void printError(String error) {
        print(error);
        flush();
    }

    public void flushStringBuilder() {
        print(stringBuilder.toString());
        flush();
    }

    private void flush() {
        stringBuilder.setLength(0);
    }

    public void printRequestMessage(LocalDateTime dateTime) {
        var formatted = DateTimeFormatter.ofPattern(REQUEST_METHOD).format(dateTime);
        print(formatted);
    }

    public void printMethod() {
        print(METHOD_MESSAGE);
    }

    public void printRequestNickName() {
        print(REQUEST_NICKNAME);
    }

    public void printRequestAttendanceTime() {
        print(REQUEST_ATTENDANCE_TIME);
    }

    public void printRequestNickNameForModify() {
        print(REQUEST_ATTENDANCE_MODIFY_NICKNAME);
    }

    public void printRequestDateForModify() {
        print(REQUEST_ATTENDANCE_MODIFY_DATE);
    }

    public void printRequestTimeForModify() {
        print(REQUEST_ATTENDANCE_MODIFY_TIME);
    }

    public void printAttendance(LocalDateTime dateTime, String status) {
        var format = String.format(ATTENDANCE_FORMAT, status);
        var formatted = DateTimeFormatter.ofPattern(format).format(dateTime);
        print(formatted);
    }

    public void appendOldAttendance(LocalDateTime dateTime, String status) {
        var format = String.format(ATTENDANCE_FORMAT, status);
        var formatted = DateTimeFormatter.ofPattern(format).format(dateTime);
        stringBuilder.append(formatted);
    }

    public void appendAbsenceAttendance(LocalDate date) {
        var formatted = DateTimeFormatter.ofPattern(FORMAT_ATTENDANCE_NOT_EXISTING).format(date);
        stringBuilder.append(formatted);
    }

    public void appendNewAttendance(LocalTime time, String status) {
        var format = String.format(MODIFY_TIME_FORM, status);
        var formatted = DateTimeFormatter.ofPattern(format).format(time);
        stringBuilder.append(formatted);
    }

    public void appendCrewNickname(String name) {
        var formatted = String.format(INITIALIZE_HISTORY, name);
        stringBuilder.append(formatted);
    }

    public void appendCrewHistoryTruancy(LocalDate date) {
        var formatted = DateTimeFormatter.ofPattern(FORMAT_ATTENDANCE_NOT_EXISTING).format(date);
        stringBuilder.append(formatted);
    }

    public void appendCrewHistory(LocalDateTime dateTime, String convertedStatus) {
        var format = String.format(ATTENDANCE_FORMAT, convertedStatus);
        var formatted = DateTimeFormatter.ofPattern(format).format(dateTime);
        stringBuilder.append(formatted);
    }

    public void appendCrewStatistics(int attendance, int late, int absence) {
        var formatted = String.format(FORMAT_STATE, attendance, late, absence);
        stringBuilder.append(formatted);
    }

    public void appendSanctionLevel(String sanctionLevel) {
        var formatted = String.format(SANCTION_LEVEL, sanctionLevel);
        stringBuilder.append(formatted);
    }

}
