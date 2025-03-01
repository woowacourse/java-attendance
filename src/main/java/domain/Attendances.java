package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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

    public Optional<Attendance> findByCrewAndDate(Crew crew, LocalDate day) {
        return attendances.stream().filter(attendance -> attendance.compareByCrewAndTime(crew, day)).findFirst();
    }

    public Attendances createMonthlyAttendances(Crew crew, LocalDate today) {
        List<Attendance> monthlyAttendances = new ArrayList<>();
        for (int i = 1; i <= today.getDayOfMonth(); i++) {
            if (Holiday.isHoliday(today.withDayOfMonth(i))) {
                continue;
            }
            LocalDate day = LocalDate.of(today.getYear(), today.getMonthValue(), i);
            Attendance attendance = findByCrewAndDate(crew, day)
                    .orElse(Attendance.createAbsence(crew, day));
            monthlyAttendances.add(attendance);
        }
        return new Attendances(monthlyAttendances);
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
