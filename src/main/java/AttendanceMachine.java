import controller.AttendanceController;
import util.DateTimeGenerator;
import util.SystemDateTimeStrategy;

public class AttendanceMachine {

    public static void main(String[] args) {
        DateTimeGenerator dateTimeGenerator = new DateTimeGenerator(new SystemDateTimeStrategy());

        AttendanceController attendanceController = new AttendanceController(dateTimeGenerator);
        attendanceController.run();
    }
}
