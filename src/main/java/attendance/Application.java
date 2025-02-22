package attendance;

import attendance.config.AppConfig;
import attendance.controller.AttendanceController;

class Application {

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        AttendanceController controller = new AttendanceController(config);

        controller.run();
    }
}
