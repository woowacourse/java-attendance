package domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance attendance) {
        validateExistAttendance(attendance);
        attendances.add(attendance);
    }

    private void validateExistAttendance(Attendance newAttendance) {
        if (attendances.stream().anyMatch(attendance -> attendance.isSameCrewAndTime(newAttendance))) {
            throw new IllegalArgumentException("출석이 이미 존재합니다.");
        }
    }

    public Attendance findByCrewAndDate(Crew crew, LocalDate day) {
        validateHoliday(day);
        return attendances.stream().filter(attendance -> attendance.compareByCrewAndTime(crew, day)).findAny()
                .orElse(Attendance.createAbsence(crew, day));
    }

    private void validateHoliday(LocalDate day) {
        if (Holiday.isHoliday(day)) {
            throw new IllegalArgumentException("주말과 공휴일에는 출석할 수 없습니다.");
        }
    }

    public Attendances createMonthlyAttendances(Crew crew, LocalDate today) {
        List<Attendance> monthlyAttendances = IntStream.rangeClosed(1, today.getDayOfMonth())
                .filter(i -> !Holiday.isHoliday(LocalDate.of(today.getYear(), today.getMonthValue(), i)))
                .mapToObj(i -> findByCrewAndDate(crew, LocalDate.of(today.getYear(), today.getMonthValue(), i)))
                .collect(Collectors.toList());

        return new Attendances(monthlyAttendances);
    }

    public Map<AttendanceType, Integer> countAttendanceType() {
        Map<AttendanceType, Integer> counts = new HashMap<>();
        for (Attendance attendance : attendances) {
            AttendanceType type = attendance.judgeType();
            counts.put(type, counts.getOrDefault(type, 0) + 1);
        }
        return counts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Attendances that = (Attendances) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
