import controller.AttendanceController;

import java.time.format.DateTimeParseException;

public class Application {
    public static void main(String[] args) {
        AttendanceController attendanceController;
        try {
            attendanceController = new AttendanceController(args);
        } catch (DateTimeParseException | ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("[ERROR] 프로그램 인수를 YYYY-MM-DD 형식으로 입력해 주세요.");
        }
        attendanceController.run();
    }
}
