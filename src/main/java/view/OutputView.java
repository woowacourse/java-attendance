package view;

import domain.AttendanceStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    private final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일");
    private final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter.ofPattern("M월 d일 E요일 HH:mm");

    public void displayMenu(LocalDate today) {
        System.out.printf("%n오늘은 %s입니다. 기능을 선택해 주세요.%n" +
                "1. 출석 확인%n" +
                "2. 출석 수정%n" +
                "3. 크루별 출석 기록 확인%n" +
                "4. 제적 위험자 확인%n" +
                "Q. 종료%n", DATE_FORMAT.format(today));
    }

    public void displayAttendanceRecord(LocalDateTime dateTime, AttendanceStatus attendanceStatus) {
        System.out.printf("%s (%s)%n", DATE_TIME_FORMAT.format(dateTime), attendanceStatus.getName());
    }
}
