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
                .filter(AttendanceDate::isValid)
                .map(storage::findByDate)
                .toList();
    }

    public AttendanceStatistic findStatisticByDateRange(String crew, LocalDate startDate, LocalDate endDate) {
        AttendanceStorage storage = findAttendanceStorageByCrew(crew);
        return storage.getStatisticByDateRange(startDate, endDate);
    }

    public Map<String, AttendanceStatistic> findRiskCrewStatistics(LocalDate startDate, LocalDate endDate) {
        Map<String, AttendanceStatistic> result = new HashMap<>();
        List<String> crews = getExpulsionRiskCrews(startDate, endDate);
        for (String crew : crews) {
            AttendanceStorage storage = storages.get(crew);
            AttendanceStatistic statistic = storage.getStatisticByDateRange(startDate, endDate);
            result.put(crew, statistic);
        }
        return result;
    }

    private List<String> getExpulsionRiskCrews(LocalDate startDate, LocalDate endDate) {
        return storages.keySet().stream()
                .filter(crew -> {
                    AttendanceStatistic statistic = storages.get(crew).getStatisticByDateRange(startDate, endDate);
                    ExpulsionRiskStatus status = statistic.getExpulsionRiskStatus();
                    return status != ExpulsionRiskStatus.NORMAL;
                })
                .toList();
    }

    private AttendanceStorage findAttendanceStorageByCrew(String crew) {
        if (!storages.containsKey(crew)) {
            throw new CrewNotExistException();
        }
        return storages.get(crew);
    }
}
