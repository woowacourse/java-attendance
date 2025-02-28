import java.util.HashMap;
import java.util.Map;

public class CrewAttendance {
    private final Map<String, AttendanceStorage> storages;

    private CrewAttendance(Map<String, AttendanceStorage> storages) {
        this.storages = storages;
    }

    public static CrewAttendance init() {
        return new CrewAttendance(new HashMap<>());
    }

    public static CrewAttendance of(Map<String, AttendanceStorage> storages) {
        return new CrewAttendance(storages);
    }
}
