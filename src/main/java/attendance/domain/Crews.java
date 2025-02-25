package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Crews {
    private final List<Crew> attendances;

    public Crews(List<Crew> attendances) {
        this.attendances = attendances;
    }

    public void attend(final String crewName, final LocalDateTime attendTime) {
        Crew crew = findByName(crewName);
        crew.attend(attendTime);
    }

    public Attendance update(final String name, final LocalDateTime newLocalDateTime) {
        Crew crew = findByName(name);
        return crew.modify(newLocalDateTime);
    }

    public WarningLevel queryWarningLevelByName(final String name, int today) {
        Crew crewAttendance = findByName(name);
        Map<AttendanceStatus, Integer> crewAttendanceStatuses = crewAttendance.countAttendanceStatus(today);
        return WarningLevel.of(crewAttendanceStatuses);
    }

    public Map<LocalDate, Attendance> queryCrewAttendance(final String name, int today) {
        Crew crewAttendance = findByName(name);
        return crewAttendance.getTimeStamps(today);
    }

    public Map<AttendanceStatus, Integer> queryCrewAttendanceStatus(final String name, int today) {
        Crew crewAttendance = findByName(name);
        return crewAttendance.countAttendanceStatus(today);
    }

    public List<String> findByWarningLevel(final WarningLevel warningLevel, int today) {
        return attendances.stream()
                .map(Crew::getName)
                .filter(name -> {
                    final Map<AttendanceStatus, Integer> crewStatuses = queryCrewAttendanceStatus(name, today);
                    WarningLevel crewWarningLevel = WarningLevel.of(crewStatuses);
                    return crewWarningLevel.equals(warningLevel);
                }).toList();
    }

    private Crew findByName(final String name) {
        return attendances.stream()
                .filter(attendance -> attendance.isNameMatch(name))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 닉네임입니다."));
    }
}
