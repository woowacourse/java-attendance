package attendance.domain;

import attendance.util.ErrorMessage;

import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance attendance) {
        validateExist(attendance);
        attendances.add(attendance);
    }

    private void validateExist(Attendance newAttendance) {
        attendances.stream()
                .filter(attendance -> attendance.isSameDate(newAttendance))
                .findFirst()
                .ifPresent(exception -> {
                    throw new IllegalArgumentException(ErrorMessage.ATTENDANCE_ALREADY_EXIST_ERROR.getMessage());
                });
    }

    public int size() {
        return attendances.size();
    }
}
