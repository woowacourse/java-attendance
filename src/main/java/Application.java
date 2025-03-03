import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Crews crews = new Crews(new ArrayList<>(List.of(new Crew("히로"))));
        AttendanceHistories attendanceHistories = new AttendanceHistories(new ArrayList<>());
        AttendanceSystemManager attendanceSystemManager = new AttendanceSystemManager(attendanceHistories, crews);
        AttendanceWorkflow attendanceWorkflow = new AttendanceWorkflow(attendanceSystemManager, crews,
                attendanceHistories);

        attendanceWorkflow.run();
    }
}
