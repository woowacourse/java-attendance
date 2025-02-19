package view;

import domain.Attendance;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class OutputView {

    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime time = attendance.getTime();
        String formattedDate = time.format(
                DateTimeFormatter.ofPattern("MM월 dd일 E요일 HH:mm").withLocale(Locale.forLanguageTag("ko"))
        );
        String formattedStatus = "(" + attendance.getStatus() + ")";
        System.out.println(formattedDate + " " + formattedStatus);
    }

    public void printExceptionMessage(String message) {
        System.out.println(message);
    }

    public void recommendModifyFunction(String message) {
        System.out.println(message + " 수정 기능을 이용해주세요.");
    }
}
