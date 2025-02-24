package attendance;

import attendance.config.AppConfig;

class Application {

    public static void main(String[] args) {
        AppConfig config = new AppConfig();
        AttendanceSystem system = new AttendanceSystem(config);

        try {
            system.run();
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
