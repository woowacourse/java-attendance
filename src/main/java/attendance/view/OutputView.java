package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.dto.AttendanceResultResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OutputView {
    public void printAttendResult(AttendanceResultResponse response) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM월 dd일 EEEE HH:mm");
        LocalDateTime dateTime = response.dateTime();
        AttendanceStatus status = response.status();
        System.out.printf("%s (%s)\n", dateTime.format(formatter), status.getMessage());
    }

    public void printErrorMessage(String massage) {
        System.out.println(massage);
    }
}
