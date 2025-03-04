import console.AttendanceSystemConsole;
import crew.Crews;
import history.AttendanceHistories;
import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Crews crews = new Crews(new ArrayList<>(List.of()));
        AttendanceHistories attendanceHistories = new AttendanceHistories(new ArrayList<>());
        AttendanceSystemConsole attendanceSystemConsole = new AttendanceSystemConsole();

        AttendanceProcessor attendanceProcessor = new AttendanceProcessor(attendanceHistories, crews);
        AttendanceWorkflow attendanceWorkflow = new AttendanceWorkflow(attendanceProcessor, crews,
                attendanceHistories, attendanceSystemConsole);

        attendanceWorkflow.run();
    }
}
