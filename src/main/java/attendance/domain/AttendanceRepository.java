package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class AttendanceRepository {
    private final List<Attendance> attendances;

    public AttendanceRepository(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(final String name, final LocalDateTime localDateTime) {
        Attendance crewAttendance = findByName(name);
        crewAttendance.add(localDateTime);
    }

    private Attendance findByName(final String name) {
        Optional<Attendance> crewAttendance = attendances.stream()
                .filter(attendance -> attendance.isNameMatch(name))
                .findAny();
        if (crewAttendance.isEmpty()) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 닉네임입니다.");
        }
        return crewAttendance.get();
    }

    public HourMinute update(final String name, final LocalDateTime newLocalDateTime) {
        LocalDate localDate = newLocalDateTime.toLocalDate();
        HourMinute hourMinute = new HourMinute(newLocalDateTime);

        Attendance crewAttendance = findByName(name);
        if (!crewAttendance.hasTimeStamp(localDate)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }
        return crewAttendance.modify(localDate, hourMinute);
    }

    public WarningLevel queryWarningLevelByName(final String name, int today) {
        Attendance crewAttendance = findByName(name);
        final Map<AttendanceStatus, Integer> crewAttendanceStatuses = crewAttendance.countAttendanceStatus(today);
        return WarningLevel.calculateLevel(crewAttendanceStatuses);
    }

    public Map<LocalDate, HourMinute> queryCrewAttendance(final String name, int today) {
        Attendance crewAttendance = findByName(name);
        return crewAttendance.getTimestamps(today);
    }

    public Map<AttendanceStatus, Integer> queryCrewAttendanceStatus(final String name, int today) {
        Attendance crewAttendance = findByName(name);
        return crewAttendance.countAttendanceStatus(today);
    }

    public List<String> queryAllNames() {
        return attendances.stream().map(Attendance::getName).toList();
    }

    public List<String> findByWarningLevel(final WarningLevel warningLevel, int today) {
        return attendances.stream().map(Attendance::getName).filter(name -> {
            final Map<AttendanceStatus, Integer> crewStatuses = queryCrewAttendanceStatus(name, today);
            WarningLevel crewWarningLevel = WarningLevel.calculateLevel(crewStatuses);
            return crewWarningLevel.equals(warningLevel);

        }).toList();
    }
}
