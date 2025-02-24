package attendance;

import attendance.config.AppConfig;

class Application {

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        AttendanceSystem system = new AttendanceSystem(config);

        system.run();
    }
}
