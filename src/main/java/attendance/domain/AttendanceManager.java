package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttendanceManager {
    private final Map<String, Attendances> crewAttendances;
    private final LocalDateProvider dateProvider;
    private final AttendanceStatistics statistics;

    public AttendanceManager(Map<String, Attendances> crewAttendances, LocalDateProvider dateProvider,
                             AttendanceStatistics statistics) {
        this.crewAttendances = crewAttendances;
        this.dateProvider = dateProvider;
        this.statistics = statistics;
    }

    public void validateExistCrew(String crewName) {
        if (!crewAttendances.containsKey(crewName)) {
            throw new IllegalArgumentException("존재하지 않는 크루 닉네임입니다.");
        }
    }

    public void attend(String crewName, LocalTime time) {
        Attendances attendances = findAttendancesByName(crewName);
        checkDuplicateAttendance(attendances, dateProvider.now());
        AttendanceChecker.checkCampusOpen(dateProvider.now(), time);

        attendances.addAttendance(dateProvider.now(), time);
    }

    private void checkDuplicateAttendance(Attendances attendances, LocalDate date) {
        if (attendances.isAttendedDate(date)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 있습니다. 출석 수정 기능을 이용해주세요.");
        }
    }

    public LocalTime modify(String crewName, LocalDate date, LocalTime time) {
        AttendanceChecker.checkCampusOpen(date, time);
        Attendances attendances = findAttendancesByName(crewName);
        Attendance prevAttendance = attendances.getCurrentAttendance(date);

        attendances.addAttendance(date, time);
        return Optional.ofNullable(prevAttendance)
                .map(Attendance::time)
                .orElse(null);
    }

    public WarningLevel calculateCrewWarningLevel(String crewName) {
        Attendances attendances = findAttendancesByName(crewName);
        Map<AttendanceStatus, Integer> statusCount = statistics.getTotalStatusCount(attendances);

        return WarningLevel.from(statusCount);
    }

    public Map<AttendanceStatus, Integer> calculateStatusCount(String crewName) {
        Attendances attendances = findAttendancesByName(crewName);
        return statistics.getTotalStatusCount(attendances);
    }

    public Map<String, Map<AttendanceStatus, Integer>> getCrewsStatusCount() {
        Map<String, Map<AttendanceStatus, Integer>> result = new HashMap<>();
        crewAttendances.keySet()
                .forEach(name -> {
                    Map<AttendanceStatus, Integer> statusCount = calculateStatusCount(name);
                    result.put(name, statusCount);
                });
        return result;
    }

    public WarningLevel calculateWarningLevel(String crewName) {
        return WarningLevel.from(calculateStatusCount(crewName));
    }

    private Attendances findAttendancesByName(String crewName) {
        validateExistCrew(crewName);
        return crewAttendances.get(crewName);
    }

    public Map<LocalDate, Attendance> getCrewAttendances(String crewName) {
        return findAttendancesByName(crewName).getAttendances();
    }
}
