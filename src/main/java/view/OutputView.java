package view;

import java.time.LocalDateTime;
import model.Student;
import util.LocalDateTimePrintFormatter;

public class OutputView {

    public static void printTodayAttendanceResult(Student student, LocalDateTime localDateTime) {
        for (LocalDateTime localDateTimeIn : student.getRecord().keySet()) {
            if (localDateTimeIn.isEqual(localDateTime)) {
                String dateAndTime = LocalDateTimePrintFormatter.LocalDateTimeToLocalTime(localDateTimeIn);
                String state = student.getRecord().get(localDateTimeIn).getState();
                System.out.println(dateAndTime + "(" + state +")");
            }
        }
    }
}
