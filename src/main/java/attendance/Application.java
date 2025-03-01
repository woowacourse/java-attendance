package attendance;

import attendance.domain.Attendances;
import attendance.view.DataLoader;
import java.util.Map;

public class Application {
    public static void main(String[] args) {
        Map<String, Attendances> attendancesMap = DataLoader.loadAttendancesData();
    }
}
