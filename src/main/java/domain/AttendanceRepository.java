package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceRepository {
    private final Map<String, List<Attendance>> attendances;

    public AttendanceRepository() {
        this.attendances = new HashMap<>();
    }

    public Map<String, List<Attendance>> getAttendances() {
        return attendances;
    }

    public void checkIn(String name, LocalDate localDate, LocalTime localTime) {
        validateWeekDay(localDate);
        attendances.putIfAbsent(name, new ArrayList<>());
        validateDuplicateCheckIn(name, localDate);
        attendances.get(name).add(new Attendance(name, localDate, localTime));
    }

    private void validateWeekDay(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || localDate.equals(
                LocalDate.of(2024, 12, 25))) {
            throw new IllegalArgumentException("주말 및 공휴일에는 출석할 수 없습니다.");
        }
    }

    private void validateDuplicateCheckIn(String name, LocalDate localDate) {
        if (attendances.get(name).stream()
                .anyMatch(attendance -> attendance.getLocalDate().equals(localDate))) {
            throw new IllegalArgumentException("이미 출석한 크루입니다.");
        }
    }

    public Attendance getAttendance(String name, LocalDate localDate) {
        List<Attendance> crewAttendances = attendances.get(name);

        return crewAttendances.stream()
                .filter(a -> a.getLocalDate().equals(localDate))
                .findFirst()
                .orElse(null);
    }
}
