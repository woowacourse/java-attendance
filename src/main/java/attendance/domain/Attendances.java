package attendance.domain;

import attendance.util.ErrorMessage;

import java.time.LocalDate;
import java.util.List;

public class Attendances {

    private final List<Attendance> attendances;

    public Attendances(List<Attendance> attendances) {
        this.attendances = attendances;
    }

    public void add(Attendance newAttendance) {
        validateAlreadyExist(newAttendance);
        attendances.add(newAttendance);
    }

    private void validateAlreadyExist(Attendance newAttendance) {
        attendances.stream()
                .filter(attendance -> attendance.isSameDate(newAttendance))
                .findFirst()
                .ifPresent(exception -> {
                    throw new IllegalArgumentException(ErrorMessage.ATTENDANCE_ALREADY_EXIST_ERROR.getMessage());
                });
    }

    public Attendance findByDate(LocalDate inputDate) {
        return attendances.stream()
                .findFirst()
                .filter(attendance -> attendance.isSameDate(inputDate))
                .orElseThrow(() -> new IllegalArgumentException(ErrorMessage.ATTENDANCE_NOT_EXIST_ERROR.getMessage()));
    }

    public void update(Attendance oldAttendance, Attendance newAttendance) {
        attendances.stream()
                .filter(attendance -> attendance.equals(oldAttendance))
                .findFirst()
                .ifPresent(attendance -> {
                    int index = attendances.indexOf(attendance);
                    attendances.set(index, newAttendance);
                });
    }
}
