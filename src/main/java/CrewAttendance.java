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

    public void create(String crew) {
        if (storages.containsKey(crew)) {
            throw new RuntimeException("이미 출석 저장소를 생성한 크루입니다.");
        }
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        storages.put(crew, attendanceStorage);
    }

    public AttendanceStorage findAttendanceStorageByCrew(String name) {
        return storages.get(name);
    }
}
