import controller.AttendanceController;
import util.DateTimeGenerator;
import util.DateTimeStrategy;
import util.SystemDateTimeStrategy;

public class AttendanceMachine {

    public static void main(String[] args) {
        DateTimeStrategy dateTimeStrategy = new SystemDateTimeStrategy();
        DateTimeGenerator dateTimeGenerator = new DateTimeGenerator(dateTimeStrategy);

        AttendanceController attendanceController = new AttendanceController(dateTimeGenerator);
        attendanceController.run();
    }
}
