package attendance.view;

import attendance.domain.AttendanceDateTime;
import attendance.domain.AttendanceStatusChecker;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Scanner;

public class AttendanceModifyView {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm", Locale.KOREA);
    public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public String readCrewNickname() {
        System.out.println("출석을 수정하려는 크루의 닉네임을 입력해 주세요.");
        return readOneLine();
    }

    public int readDayToModify() {
        System.out.println("수정하려는 날짜(일)를 입력해 주세요.");
        String numberText = readOneLine();
        NumberParser numberParser = new NumberParser();
        return numberParser.parse(numberText);
    }

    public LocalTime readTimeToModify() {
        System.out.println("언제로 변경하겠습니까?");
        String timeInput = readOneLine();
        try {
            return LocalTime.parse(timeInput, TIME_FORMATTER);
        } catch (DateTimeParseException exception) {
            throw new IllegalArgumentException("시간을 올바르게 입력해 주세요.");
        }
    }

    private String readOneLine() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void printAttendanceModifyResult(AttendanceDateTime originalDateTime,
                                            AttendanceStatusChecker.AttendanceStatus originalAttendanceStatus,
                                            AttendanceDateTime newDateTime,
                                            AttendanceStatusChecker.AttendanceStatus newAttendanceStatus) {
        AttendanceStatusTextMaker attendanceStatusTextMaker = new AttendanceStatusTextMaker();
        LocalDateTime originalLocalDateTime = originalDateTime.getLocalDateTime();
        String originalAttendanceStatusText = attendanceStatusTextMaker.make(originalAttendanceStatus);
        LocalDateTime newLocalDateTIme = newDateTime.getLocalDateTime();
        String newAttendanceStatusText = attendanceStatusTextMaker.make(newAttendanceStatus);
        System.out.printf(DATE_TIME_FORMATTER.format(originalLocalDateTime) + " (%s)".formatted(originalAttendanceStatusText)
                + " -> " + TIME_FORMATTER.format(newLocalDateTIme) + " (%s)".formatted(newAttendanceStatusText)
                + " 수정 완료!");
    }
}
