import exception.CrewNotExistException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class CrewAttendanceStorage {
    private final Map<String, AttendanceStorage> storages;

    private CrewAttendanceStorage(Map<String, AttendanceStorage> storages) {
        this.storages = storages;
    }

    public static CrewAttendanceStorage init() {
        return new CrewAttendanceStorage(new HashMap<>());
    }

    public static CrewAttendanceStorage of(Map<String, AttendanceStorage> storages) {
        return new CrewAttendanceStorage(storages);
    }

    public void create(String crew) {
        if (storages.containsKey(crew)) {
            throw new RuntimeException("이미 출석 저장소를 생성한 크루입니다.");
        }
        AttendanceStorage attendanceStorage = new AttendanceStorage();
        storages.put(crew, attendanceStorage);
    }

    public AttendanceStorage findAttendanceStorageByCrew(String crew) {
        if (!storages.containsKey(crew)) {
            throw new CrewNotExistException();
        }
        return storages.get(crew);
    }

    public void register(String crew, LocalDate date, LocalTime time) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        storage.register(date, time);
    }

    public Optional<Attendance> findAttendance(String crew, LocalDate date) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return storage.findByDate(date);
    }
}
