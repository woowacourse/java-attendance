import controller.AttendanceSystem;
import domain.AttendanceBook;
import java.util.Scanner;
import util.DateTimeManager;
import util.FileReader;

public class AttendanceApplication {

    public static void main(String[] args) {
        AttendanceBook attendanceBook = FileReader.readExistedAttendanceData();
        DateTimeManager dateTimeManager = new DateTimeManager(2024, 12, 13);
        Scanner scanner = new Scanner(System.in);
        AttendanceSystem attendanceSystem = new AttendanceSystem(attendanceBook, dateTimeManager, scanner);
        attendanceSystem.run();
    }
}
