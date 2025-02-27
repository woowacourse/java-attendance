package domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AttendanceRepository {
    private final Map<String, List<Attendance>> attendances;

    public AttendanceRepository(Map<String, List<Attendance>> initialAttendances) {
        this.attendances = initialAttendances;
    }

    public Map<String, List<Attendance>> getAttendances() {
        return attendances;
    }

    public void checkIn(String name, LocalDate localDate, LocalTime localTime) {
        validateWeekDay(localDate);
        validateExistingCrew(name);
        validateDuplicateCheckIn(name, localDate);
        attendances.get(name).add(new Attendance(name, localDate, localTime));
    }

    private void validateDuplicateCheckIn(String name, LocalDate localDate) {
        if (attendances.get(name).stream()
                .anyMatch(attendance -> attendance.getLocalDate().equals(localDate))) {
            throw new IllegalArgumentException("이미 출석한 크루입니다.");
        }
    }

    public Attendance update(String name, LocalDate localDate, LocalTime localTime) {
        validateWeekDay(localDate);
        validateExistingCrew(name);
        validateAfterToday(localDate);

        List<Attendance> crewAttendances = attendances.get(name);
        Attendance beforeAttendance = getBeforeAttendance(localDate, crewAttendances);
        List<Attendance> newAttendances = getNewAttendances(localDate, crewAttendances);

        newAttendances.add(beforeAttendance.updateTime(localTime));
        attendances.put(name, List.copyOf(newAttendances));

        return beforeAttendance;
    }

    private void validateWeekDay(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || localDate.equals(
                LocalDate.of(2024, 12, 25))) {
            throw new IllegalArgumentException("주말 및 공휴일에는 출석할 수 없습니다.");
        }
    }

    private void validateExistingCrew(String name) {
        if (!attendances.containsKey(name)) {
            throw new IllegalArgumentException("존재하는 크루의 닉네임을 입력해주세요.");
        }
    }

    private void validateAfterToday(LocalDate localDate) {
        if (localDate.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("수정할 수 없는 날짜입니다.");
        }
    }

    private static Attendance getBeforeAttendance(LocalDate localDate, List<Attendance> crewAttendances) {
        return crewAttendances.stream()
                .filter(a -> a.getLocalDate().equals(localDate))
                .findFirst()
                .orElse(null);
    }

    private static List<Attendance> getNewAttendances(LocalDate localDate, List<Attendance> crewAttendances) {
        return crewAttendances.stream()
                .filter(a -> !a.getLocalDate().equals(localDate))
                .collect(Collectors.toList());
    }

    public Attendance getAttendance(String name, LocalDate localDate) {
        List<Attendance> crewAttendances = attendances.get(name);

        return crewAttendances.stream()
                .filter(a -> a.getLocalDate().equals(localDate))
                .findFirst()
                .orElse(null);
    }
}
