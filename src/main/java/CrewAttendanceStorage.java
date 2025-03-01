import exception.CrewNotExistException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        AttendanceStorage attendanceStorage = AttendanceStorage.init();
        storages.put(crew, attendanceStorage);
    }

    public boolean modify(String crew, LocalDate date, LocalTime time) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return storage.modify(date, time);
    }

    public boolean register(String crew, LocalDate date, LocalTime time) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return storage.register(date, time);
    }

    public Attendance findAttendance(String crew, LocalDate date) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return storage.findByDate(date);
    }

    public List<Attendance> findAttendanceByDateRange(String crew, LocalDate startDate, LocalDate endDate) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return startDate.datesUntil(endDate)
                .map(storage::findByDate)
                .toList();
    }

    public AttendanceStatistic findStatisticByDateRange(String crew, LocalDate startDate, LocalDate endDate) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return storage.getStatisticByDateRange(startDate, endDate);
    }

    private AttendanceStorage findAttendanceStorageByCrew(String crew) {
        if (!storages.containsKey(crew)) {
            throw new CrewNotExistException();
        }
        return storages.get(crew);
    }
}
