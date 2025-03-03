package view;

import domain.AttendanceDateTime;
import domain.AttendanceDateTimes;
import domain.AttendanceStatus;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.NoSuchElementException;

public class OutputView {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일");
    private final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");
    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일 HH:mm");

    public void displayMenu(LocalDate today) {
        System.out.printf("%n오늘은 %s입니다. 기능을 선택해 주세요.%n" +
                "1. 출석 확인%n" +
                "2. 출석 수정%n" +
                "3. 크루별 출석 기록 확인%n" +
                "4. 제적 위험자 확인%n" +
                "Q. 종료%n", DATE_FORMAT.format(today));
    }

    public void displayAttendanceRecord(LocalDateTime dateTime) {
        System.out.println(toAttendanceRecordFormat(new AttendanceDateTime(dateTime)));
    }

    public void displayUpdateResult(AttendanceDateTime oldAttendanceDateTime, LocalDateTime newAttendanceDateTime) {
        System.out.printf("%n%s -> %s (%s) 수정 완료!%n"
                , toAttendanceRecordFormat(oldAttendanceDateTime)
                , TIME_FORMAT.format(newAttendanceDateTime)
                , AttendanceStatus.of(newAttendanceDateTime).getName()
        );
    }

    public void displayAttendanceDateTimes(Crew crew, AttendanceDateTimes attendanceDateTimes, LocalDate today) {
        System.out.printf("%n%s의 출석 기록입니다.%n%n", crew.nickname());

        for (LocalDate date = LocalDate.of(2025, 2, 11); date.isBefore(today); date = date.plusDays(1)) {
            displayAttendanceDateTime(attendanceDateTimes, date);
        }
    }

    private void displayAttendanceDateTime(AttendanceDateTimes attendanceDateTimes, LocalDate date) {
        try {
            AttendanceDateTime attendanceDateTime = attendanceDateTimes.get(date);
            System.out.printf(toAttendanceRecordFormat(attendanceDateTime));
        } catch (NoSuchElementException e) {
            System.out.printf(toEmptyRecordFormat(date));
        }
        System.out.println();
    }

    private String toAttendanceRecordFormat(AttendanceDateTime attendanceDateTime) {
        LocalDateTime dateTime = attendanceDateTime.getLocalDateTime();
        AttendanceStatus status = attendanceDateTime.getStatus();
        return String.format("%s (%s)", DATE_TIME_FORMAT.format(dateTime), status.getName());
    }

    private String toEmptyRecordFormat(LocalDate date) {
        return String.format("%s --:-- (결석)", DATE_FORMAT.format(date));
    }
}
