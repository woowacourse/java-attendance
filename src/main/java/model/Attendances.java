package model;

import java.util.Collections;
import java.util.List;

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
