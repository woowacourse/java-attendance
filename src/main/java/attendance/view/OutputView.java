package attendance.view;

import attendance.model.AttendanceType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {

    public void printDate(LocalDate date) {
        System.out.print(date.format(DateTimeFormatter.ofPattern("오늘은 MM월 dd일 E요일입니다. ")));
    }

    public void printAttend(LocalDateTime dateTime, AttendanceType attendanceType) {
        System.out.print(dateTime.format(DateTimeFormatter.ofPattern("\nMM월 dd일 E요일 HH:mm ")));
        System.out.printf("(%s)%n", attendanceType.getKoreanLabel());
    }
}
