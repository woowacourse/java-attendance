package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Attendances {

    private final List<Attendance> attendances;

    private Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public static Attendances of(List<Attendance> attendances) {
        return new Attendances(attendances);
    }

    public boolean contains(Attendance attendance) {
        return attendances.stream().anyMatch(attendance::equals);
    }

    public void checkIn(Attendance attendance) {
        validateExistAttendance(attendance);

        attendances.add(attendance);
    }

    public Attendance modify(Crew crew, LocalDateTime modifiedCheckInTime) {
        Optional<Attendance> existAttendance = find(crew, modifiedCheckInTime.toLocalDate());
        if (existAttendance.isPresent()) {
            existAttendance.get().modify(modifiedCheckInTime.toLocalTime());
            return existAttendance.get();
        }
        Attendance attendance = Attendance.of(crew, modifiedCheckInTime);
        checkIn(attendance);

        return attendance;
    }

    public Optional<Attendance> find(Crew crew, LocalDate localDate) {
        for (Attendance attendance : attendances) {
            if (attendance.isSame(crew, localDate)) {
                Attendance copy = attendance.clone(attendance);
                return Optional.of(copy);
            }
        }
        return Optional.empty();
    }

    private void validateExistAttendance(Attendance newAttendance) {
        attendances.stream()
                .filter(attendance -> attendance.isSameDateAndCrew(newAttendance))
                .findAny()
                .ifPresent(error -> {
                    throw new IllegalArgumentException("이미 출석한 경우에는 다시 출석할 수 없습니다.");
                });
    }

    public List<Attendance> getAttendances() {
        return Collections.unmodifiableList(attendances);
    }
}
