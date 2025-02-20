import controller.AttendanceSystem;
import util.DateGenerator;

public class Main {
    public static void main(String[] args) {
        DateGenerator dateGenerator = new DateGenerator(2024, 12, 13);
        AttendanceSystem attendanceSystem = new AttendanceSystem(dateGenerator);

        attendanceSystem.start();
    }
}
