package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttendanceManager {
    private final Map<String, Attendances> crewAttendances;

    public AttendanceManager(Map<String, Attendances> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void validateExistCrew(String crewName) {
        if (!crewAttendances.containsKey(crewName)) {
            throw new IllegalArgumentException("존재하지 않는 크루 닉네임입니다.");
        }
    }

    public void attend(String crewName, LocalDate date, LocalTime time) {
        Attendances attendances = findAttendancesByName(crewName);
        checkDuplicateAttendance(attendances, date);
        AttendanceChecker.checkCampusOpen(date, time);

        attendances.addAttendance(date, time);
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

    public WarningLevel calculateCrewWarningLevel(String crewName, int today) {
        Attendances attendances = findAttendancesByName(crewName);
        Map<AttendanceStatus, Integer> statusCount = AttendanceStatistics.getTotalStatusCount(attendances, today);

        return WarningLevel.from(statusCount);
    }

    public Map<AttendanceStatus, Integer> calculateStatusCount(String crewName, int today) {
        Attendances attendances = findAttendancesByName(crewName);
        return AttendanceStatistics.getTotalStatusCount(attendances, today);
    }

    public Map<String, Map<AttendanceStatus, Integer>> getCrewsStatusCount(int today) {
        Map<String, Map<AttendanceStatus, Integer>> result = new HashMap<>();
        crewAttendances.keySet()
                .forEach(name -> {
                    Map<AttendanceStatus, Integer> statusCount = calculateStatusCount(name, today);
                    result.put(name, statusCount);
                });
        return result;
    }

    public WarningLevel calculateWarningLevel(String crewName, int today) {
        return WarningLevel.from(calculateStatusCount(crewName, today));
    }

    private Attendances findAttendancesByName(String crewName) {
        validateExistCrew(crewName);
        return crewAttendances.get(crewName);
    }

    public Map<LocalDate, Attendance> getCrewAttendances(String crewName) {
        return findAttendancesByName(crewName).getAttendances();
    }
}
