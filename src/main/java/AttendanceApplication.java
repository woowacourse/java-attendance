import presentation.AttendanceController;
import presentation.view.AttendanceFileInputView;
import domain.CrewFactory;

public class AttendanceApplication {
    public static void main(String[] args) {
        AttendanceFileInputView fileInputView = new AttendanceFileInputView();
        CrewFactory crewFactory = new CrewFactory();
        AttendanceController attendanceController = new AttendanceController(fileInputView, crewFactory);
        attendanceController.run();
    }
}
