import controller.AttendanceController;
import domain.AttendanceBook;
import dto.InitialInformation;
import util.FileReader;

public class AttendanceApplication {
    public static void main(String[] args) {
        InitialInformation initialInformation = new FileReader().readAttendanceData();
        AttendanceController attendanceController = new AttendanceController();
        attendanceController.run(new AttendanceBook(initialInformation));
    }
}
