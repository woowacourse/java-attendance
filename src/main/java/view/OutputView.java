package view;

import controller.DateTimeConverter;
import java.time.LocalDateTime;

public class OutputView {
    public void printError(String message) {
        System.out.println("[ERROR] " + message);
    }

    public void printAttend(LocalDateTime attendedTime, String attendanceStatus) {
        System.out.println(DateTimeConverter.convertLocalDateToString(attendedTime.toLocalDate()) + " "
                + DateTimeConverter.convertLocalTimeToString(attendedTime.toLocalTime())
                + " (" + attendanceStatus + ")");
    }
}
