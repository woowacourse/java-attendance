package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CrewAttendanceRepository {
    private final List<CrewAttendance> crewAttendances;

    public CrewAttendanceRepository(List<CrewAttendance> crewAttendances) {
        this.crewAttendances = crewAttendances;
    }

    public void add(final String name, final LocalDateTime localDateTime) {
        CrewAttendance crewAttendance = findByName(name);
        crewAttendance.add(localDateTime);
    }

    private CrewAttendance findByName(final String name) {
        Optional<CrewAttendance> crewAttendance = crewAttendances.stream()
                .filter(attendance -> attendance.isNameMatch(name))
                .findAny();

        return crewAttendance.orElseThrow(() -> new IllegalArgumentException("[ERROR] 유효하지 않은 닉네임입니다."));
    }

    public AttendanceTimeStatus update(final String name, final LocalDateTime newLocalDateTime) {
        LocalDate localDate = newLocalDateTime.toLocalDate();
        AttendanceTimeStatus newAttendanceTimeStatus = new AttendanceTimeStatus(newLocalDateTime);

        CrewAttendance crewAttendance = findByName(name);
        if (!crewAttendance.hasAttendanceOn(localDate)) {
            throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
        }
        return crewAttendance.modify(localDate, newAttendanceTimeStatus);
    }

    public WarningLevel queryWarningLevelByName(final String name, int today) {
        CrewAttendance crewAttendance = findByName(name);
        final Map<AttendanceStatus, Integer> attendanceStatusCounts = crewAttendance.countAttendanceStatus(today);
        return WarningLevel.calculateLevel(attendanceStatusCounts);
    }

    public Map<LocalDate, AttendanceTimeStatus> queryCrewAttendance(final String name, int today) {
        CrewAttendance crewAttendance = findByName(name);
        return crewAttendance.getAttendances(today);
    }

    public Map<AttendanceStatus, Integer> queryCrewAttendanceStatus(final String name, int today) {
        CrewAttendance crewAttendance = findByName(name);
        return crewAttendance.countAttendanceStatus(today);
    }

    public List<String> findByWarningLevel(final WarningLevel warningLevel, int today) {
        return crewAttendances.stream()
                .map(CrewAttendance::getName)
                .filter(name -> {
                    final Map<AttendanceStatus, Integer> attendanceStatusCounts = queryCrewAttendanceStatus(name, today);
                    WarningLevel crewWarningLevel = WarningLevel.calculateLevel(attendanceStatusCounts);
                    return crewWarningLevel.equals(warningLevel);

                }).toList();
    }
}
