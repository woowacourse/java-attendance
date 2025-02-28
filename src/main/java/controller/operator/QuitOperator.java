package controller.operator;

import domain.AttendanceBook;
import java.time.LocalDate;

public class QuitOperator implements OptionOperator {

    @Override
    public void process(AttendanceBook attendanceBook, LocalDate attendanceDate) {
        System.exit(0);
    }
}
