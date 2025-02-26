package attendance.domain;

import static attendance.domain.WarningLevel.NONE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final Map<String, CrewAttendance> crewAttendances;

    public AttendanceBook(final Map<String, CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void add(final String name, final LocalDateTime localDateTime) {
        validateName(name);
        CrewAttendance crewAttendance = crewAttendances.get(name);
        crewAttendance.add(localDateTime);
    }

    public AttendanceBeforeAfter modify(final String name, final LocalDateTime newLocalDateTime) {
        validateName(name);
        CrewAttendance crewAttendance = crewAttendances.get(name);
        LocalDate targetDate = newLocalDateTime.toLocalDate();

        AttendanceTimeStatus prevAttendanceTimeStatus = crewAttendance.getAttendanceOn(targetDate);
        crewAttendance.modify(newLocalDateTime);
        AttendanceTimeStatus newAttendanceTimeStatus = crewAttendance.getAttendanceOn(targetDate);

        return new AttendanceBeforeAfter(prevAttendanceTimeStatus, newAttendanceTimeStatus);
    }

    private void validateName(final String name) {
        if (!crewAttendances.containsKey(name)) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 닉네임입니다.");
        }
    }

    public Map<LocalDate, AttendanceTimeStatus> getAttendanceHistory(final String name, LocalDate date) {
        CrewAttendance crewAttendance = crewAttendances.get(name);
        return crewAttendance.getAttendancesBefore(date);
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatusCounts(final String name, LocalDate date) {
        CrewAttendance crewAttendance = crewAttendances.get(name);
        return crewAttendance.countAttendanceStatusBefore(date);
    }

    public WarningLevel getCrewWarningLevel(final String name, LocalDate date) {
        final Map<AttendanceStatus, Integer> attendanceStatusCounts = getAttendanceStatusCounts(name, date);
        return WarningLevel.calculateLevel(attendanceStatusCounts);
    }

    public Map<WarningLevel, List<CrewAttendance>> getCrewsByWarningLevel(final LocalDate today) {
        Map<WarningLevel, List<CrewAttendance>> warningCrews = new EnumMap<>(WarningLevel.class);
        for (WarningLevel warningLevel : WarningLevel.values()) {
            List<CrewAttendance> crewAttendances = findByWarningLevel(warningLevel, today);
            warningCrews.put(warningLevel, crewAttendances);
        }
        warningCrews.remove(NONE);
        return warningCrews;
    }

    private List<CrewAttendance> findByWarningLevel(final WarningLevel warningLevel, final LocalDate today) {
        return crewAttendances.values().stream()
                .filter(crewAttendance -> crewAttendance.hasSameWarningLevel(warningLevel, today))
                .toList();
    }
}
