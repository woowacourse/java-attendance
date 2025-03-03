import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        Crews crews = new Crews(new ArrayList<>(List.of(new Crew("히로"))));
        AttendanceHistories attendanceHistories = new AttendanceHistories(new ArrayList<>());
        AttendanceProcessor attendanceProcessor = new AttendanceProcessor(attendanceHistories, crews);
        AttendanceWorkflow attendanceWorkflow = new AttendanceWorkflow(attendanceProcessor, crews,
                attendanceHistories);

        attendanceWorkflow.run();
    }
}
