package attendance;

import attendance.configuration.ApplicationConfiguration;
import attendance.controller.AttendanceController;

public class Application {

    public static void main(String[] args) {
        ApplicationConfiguration configuration = new ApplicationConfiguration();
        AttendanceController controller = new AttendanceController(configuration);
        controller.run();
    }
}
