package attendance.view;

import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceStatusChecker.AttendanceStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class AttendanceConfirmView {

    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm");

    public String readCrewNickname() {
        System.out.println("닉네임을 입력해 주세요.");
        return readOneLine();
    }

    public LocalDateTime readAttendanceTime() {
        System.out.println("등교 시간을 입력해 주세요.");
        String timeInput = readOneLine();
        try {
            LocalDate attendanceDate = LocalDate.now();
            LocalTime attendanceTime = LocalTime.parse(timeInput, TIME_FORMATTER);
            return LocalDateTime.of(attendanceDate, attendanceTime);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("시간은 'HH:mm' 형식으로 입력해 주세요.");
        }
    }

    public void printAttendanceResult(AttendanceDateTime attendanceDateTime, AttendanceStatus attendanceStatus) {
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
        LocalDateTime attendanceLocalDateTime = attendanceDateTime.getLocalDateTime();
        String attendanceStatusText = attendanceStatusTextMaker.make(attendanceStatus);
        System.out.println(DATE_TIME_FORMATTER.format(attendanceLocalDateTime)
                + " (%s)".formatted(attendanceStatusText));
    }

    private String readOneLine() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
