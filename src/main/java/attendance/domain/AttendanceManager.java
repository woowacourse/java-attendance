package attendance.domain;

import attendance.utility.DateGenerator;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceManager {

    private final Map<String, Attendances> attendances;
    private final Holiday holiday;
    private final DateGenerator dateGenerator;

    public AttendanceManager(Holiday holiday, DateGenerator dateGenerator) {
        this.attendances = new HashMap<>();
        this.holiday = holiday;
        this.dateGenerator = dateGenerator;
    }

    public void addCrew(String name) {
        int day = dateGenerator.now().getDayOfMonth();

        Attendances newAttendances = new Attendances();
        IntStream.range(1, day + 1)
                .mapToObj(index -> LocalDateTime.of(dateGenerator.now().withDayOfMonth(index), LocalTime.MAX))
                .filter(dateTime -> !holiday.isHoliday(dateTime.toLocalDate()))
                .forEach(newAttendances::addAttendance);

        attendances.put(name, newAttendances);
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

    public List<Attendance> getAttendanceRecord(final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);
        return attendances.getAttendancesUntilYesterday();
    }

    public AttendanceStatus getAttendanceStatus(final String nickname) {
        Attendances attendances = findCrewAttendance(nickname);

        List<Attendance> attendancesUntilYesterday = attendances.getAttendancesUntilYesterday();
        return AttendanceStatus.of(attendancesUntilYesterday);
    }

    public Map<String, AttendanceStatus> getAttendanceWarnedCrews() {
        return createWarnedCrews().entrySet().stream()
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

    private LinkedHashMap<String, AttendanceStatus> createWarnedCrews() {
        return attendances.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> new AttendanceStatus(entry.getValue().getAttendancesUntilYesterday()),
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
        return attendances.containsKey(nickname);
    }

    public Attendances findCrewAttendance(String nickname) {
        return attendances.get(nickname);
    }
}
