package attendance.domain;

import static attendance.domain.WarningLevel.NONE;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private final CrewAttendanceRepository crewAttendanceRepository;

    public AttendanceBook(final CrewAttendanceRepository crewAttendanceRepository) {
        this.crewAttendanceRepository = crewAttendanceRepository;
    }

    public void add(final String name, final LocalDateTime localDateTime) {
        CrewAttendance crewAttendance = crewAttendanceRepository.findByName(name);
        crewAttendance.add(localDateTime);
    }

    public List<AttendanceTimeStatus> modify(final String name, final LocalDateTime newLocalDateTime) {
        CrewAttendance crewAttendance = crewAttendanceRepository.findByName(name);
        LocalDate targetDate = newLocalDateTime.toLocalDate();

        AttendanceTimeStatus prevAttendanceTimeStatus = crewAttendance.getAttendanceOn(targetDate);
        crewAttendance.modify(newLocalDateTime);
        AttendanceTimeStatus newAttendanceTimeStatus = crewAttendance.getAttendanceOn(targetDate);

        return List.of(prevAttendanceTimeStatus, newAttendanceTimeStatus);
    }

    public Map<LocalDate, AttendanceTimeStatus> queryAttendancesByName(final String name) {
        CrewAttendance crewAttendance = crewAttendanceRepository.findByName(name);
        return crewAttendance.queryAttendancesBefore(LocalDate.now());
    }

    public Map<AttendanceStatus, Integer> queryAttendanceStatusByName(final String name) {
        CrewAttendance crewAttendance = crewAttendanceRepository.findByName(name);
        return crewAttendance.countAttendanceStatusBefore(LocalDate.now());
    }

    public WarningLevel queryCrewWarningLevel(final String name) {
        final Map<AttendanceStatus, Integer> attendanceStatusCounts = queryAttendanceStatusByName(name);
        return WarningLevel.calculateLevel(attendanceStatusCounts);
    }

    public Map<WarningLevel, List<CrewAttendance>> queryCrewsByWarningLevel() {
        Map<WarningLevel, List<CrewAttendance>> warningCrews = new EnumMap<>(WarningLevel.class);
        for (WarningLevel warningLevel : WarningLevel.values()) {
            List<CrewAttendance> crewAttendances = crewAttendanceRepository.findByWarningLevel(warningLevel);
            warningCrews.put(warningLevel, crewAttendances);
        }
        warningCrews.remove(NONE);
        return warningCrews;
    }
}
