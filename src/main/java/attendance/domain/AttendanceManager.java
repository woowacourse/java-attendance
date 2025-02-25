package attendance.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceManager {

    private final Map<String, Attendances> crewAttendances;

    public AttendanceManager() {
        this.crewAttendances = new HashMap<>();
    }

    public void addCrew(final String name, final Attendances attendances) {
        crewAttendances.put(name, attendances);
    }

    public Attendance processAttendanceCheck(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        attendances.validateAlreadyAttendance(dateTime.toLocalDate());

        attendances.deleteAttendance(dateTime.toLocalDate());
        return attendances.addAttendance(dateTime);
    }

    public AttendanceUpdate processAttendanceUpdate(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);

        Attendance beforeAttendance = attendances.deleteAttendance(dateTime.toLocalDate());
        Attendance afterAttendance = attendances.addAttendance(dateTime);
        return new AttendanceUpdate(List.of(beforeAttendance, afterAttendance));
    }

    public Attendances getAttendanceRecord(final LocalDate today, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        List<Attendance> before = attendances.getAttendancesBefore(today);
        return Attendances.of(before);
    }

    public AttendanceStatus getAttendanceStatus(final LocalDate today, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);

        List<Attendance> attendancesBeforeToday = attendances.getAttendancesBefore(today);
        return AttendanceStatus.of(attendancesBeforeToday);
    }

    public AttendanceRiskCrews getAttendanceRiskCrew(final LocalDate today) {
        return AttendanceRiskCrews.of(generateAttendanceRisks(today));
    }

    public void validateNicknameExists(final String nickname) {
        if (!containsNickname(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public boolean containsNickname(final String nickname) {
        return crewAttendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(final String nickname) {
        return crewAttendances.get(nickname);
    }

    private Map<String, AttendanceStatus> generateAttendanceRisks(final LocalDate today) {
        return crewAttendances.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new AttendanceStatus(entry.getValue().getAttendancesBefore(today)),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }
}
