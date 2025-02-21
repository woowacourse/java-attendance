import controller.AttendanceSystem;
import domain.AttendanceBook;
import util.DateTimeManager;
import util.FileReader;

public class AttendanceApplication {

    public static void main(String[] args) {
        AttendanceBook attendanceBook = FileReader.readExistedAttendanceData();
        DateTimeManager dateTimeManager = new DateTimeManager(2024, 12, 13);
        AttendanceSystem attendanceSystem = new AttendanceSystem(attendanceBook, dateTimeManager);
        attendanceSystem.run();
    }
}
