import controller.AttendanceManagementController;
import controller.TodayDateGenerator;

public class Application {
    public static void main(String[] args) {
        TodayDateGenerator todayDateGenerator = new TodayDateGenerator();
        AttendanceManagementController attendanceManagementController = new AttendanceManagementController(todayDateGenerator);
        attendanceManagementController.attendanceManagementStart();
    }
}
