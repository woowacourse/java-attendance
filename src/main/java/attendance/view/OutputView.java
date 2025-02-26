package attendance.view;

import attendance.domain.AttendanceRecord;
import java.time.format.DateTimeFormatter;

public class OutputView {
    private final static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");

    public void displayAttendanceResult(AttendanceRecord record) {
        System.out.printf(
                "%s (%s)%n", record.getAttendanceDateTime().format(DATE_FORMATTER),
                record.getAttendanceStatus().getTitle());
    }

    public void printError(String message) {
        System.out.println(message);
    }
}
