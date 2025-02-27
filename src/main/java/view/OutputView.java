package view;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {
    private static final String INPUT_METHOD = "기능을 선택해 주세요.\n1. 출석 확인\n"
            + "2. 출석 수정\n"
            + "3. 크루별 출석 기록 확인\n"
            + "4. 제적 위험자 확인\n"
            + "Q. 종료";

    public static final String TODAY_FORMAT = "오늘은 MM월 dd일 E요일입니다. ";
    private static final DateTimeFormatter TODAY_FORMATTER = DateTimeFormatter.ofPattern(TODAY_FORMAT,
            Locale.KOREA);
    private static final String INPUT_NICKNAME = "닉네임을 입력해주세요.";
    private static final String INPUT_MODIFY_NICKNAME = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private static final String ADD_ATTENDANCE_INPUT_TIME = "등교 시간을 입력해 주세요.";
    private static final String DATE_DAY_WEEK_FORMAT = "MM월 DD일 E요일";
    private static final DateTimeFormatter DATE_DAY_WEEK_FORMATTER = DateTimeFormatter.ofPattern(DATE_DAY_WEEK_FORMAT,
            Locale.KOREA);
    private static final String TIME_FORMAT = "HH:MM";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern(TIME_FORMAT, Locale.KOREA);
    private static final String ATTENDANCE_STATUS_FORMAT = "(%s)";


    private String ERROR_PREFIX = "[ERROR] ";

    public void printError(String errorMessage) {
        println(ERROR_PREFIX + errorMessage);
    }

    public void println(String message) {
        System.out.println(message);
    }

    public void printInputMethod() {
        LocalDate date = LocalDate.now();
        String inputToday = date.format(TODAY_FORMATTER);
        println(inputToday);
        println(INPUT_METHOD);
    }

    public void printInputNickname() {
        println(INPUT_NICKNAME);
    }

    public void printInputModifyNickname() {
        println(INPUT_MODIFY_NICKNAME);
    }

    public void printAddAttendanceInputTime() {
        println(ADD_ATTENDANCE_INPUT_TIME);
    }


    public void printAttendanceDateTime(String status, LocalTime attendanceTime, LocalDate attendanceDate) {
        LocalDateTime dateTime = LocalDateTime.of(attendanceDate, attendanceTime);
        String attendanceDateTime = dateTime.format(DATE_DAY_WEEK_FORMATTER);
        print(attendanceDateTime);
        printAttendanceTime(attendanceTime);
        printAttendanceStatus(status);
    }

    private void print(String message) {
        System.out.println(message);
    }

    private void printAttendanceStatus(String status) {
        println(String.format(ATTENDANCE_STATUS_FORMAT, status));
    }

    private void printAttendanceTime(LocalTime attendanceTime) {
        String resultAttendanceTime = attendanceTime.format(TIME_FORMATTER);
        print(resultAttendanceTime);
    }
}


