import controller.AttendanceSystem;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        AttendanceSystem attendanceSystem = new AttendanceSystem(LocalDate.of(2024, 12, 13));
        attendanceSystem.start();
    }
}
