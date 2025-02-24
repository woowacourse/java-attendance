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

    public void addCrew(String name, Attendances attendances) {
        crewAttendances.put(name, attendances);
    }

    public Attendance processAttendanceCheck(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        attendances.validateAlreadyAttendance(dateTime.toLocalDate());

        attendances.deleteAttendance(dateTime.toLocalDate());
        return attendances.addAttendance(dateTime);
    }

    public List<Attendance> processAttendanceUpdate(final LocalDateTime dateTime, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);

        Attendance oldAttendance = attendances.deleteAttendance(dateTime.toLocalDate());
        Attendance newAttendance = attendances.addAttendance(dateTime);

        return List.of(oldAttendance, newAttendance);
    }

    public List<Attendance> getAttendanceRecord(final LocalDate today, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        return attendances.getAttendancesBefore(today);
    }

    public AttendanceStatus getAttendanceStatus(final LocalDate today, final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);

        List<Attendance> attendancesBeforeToday = attendances.getAttendancesBefore(today);
        return AttendanceStatus.of(attendancesBeforeToday);
    }

    public Map<String, AttendanceStatus> getAttendanceRiskCrew(final LocalDate today) {
        return createWarnedCrews(today).entrySet().stream()
                .filter(entry -> entry.getValue().isNotNoneState())
                .sorted(Map.Entry.<String, AttendanceStatus>comparingByValue()
                        .thenComparing(Map.Entry.comparingByKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    private LinkedHashMap<String, AttendanceStatus> createWarnedCrews(final LocalDate today) {
        return crewAttendances.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new AttendanceStatus(entry.getValue().getAttendancesBefore(today)),
                        (existing, replacement) -> existing,
                        LinkedHashMap::new
                ));
    }

    public void validateNicknameExists(String nickname) {
        if (!containsNickname(nickname)) {
            throw new IllegalArgumentException("[ERROR] 등록되지 않은 닉네임입니다.");
        }
    }

    public boolean containsNickname(String nickname) {
        return crewAttendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(String nickname) {
        return crewAttendances.get(nickname);
    }
}
