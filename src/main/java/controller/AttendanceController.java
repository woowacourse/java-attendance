package controller;

import domain.AttendanceBook;
import util.AttendanceBookParser;

import java.time.format.DateTimeFormatter;

public class AttendanceController {

    public static void main(String[] args) {
        AttendanceController attendanceController = new AttendanceController();
        attendanceController.run();
    }

    public static final String FILE_PATH = "src/main/resources/attendances.csv";
    public static final String YEAR_MONTH_DAY_FORMAT = "yyyy-MM-dd";
    public static final String HOUR_MINUTE_FORMAT = "HH:mm";

    public void run() {
        AttendanceBook attendanceBook = AttendanceBookParser.parseToAttendanceBook(
                FILE_PATH,
                DateTimeFormatter.ofPattern(YEAR_MONTH_DAY_FORMAT),
                DateTimeFormatter.ofPattern(HOUR_MINUTE_FORMAT)
        );
    }
}
