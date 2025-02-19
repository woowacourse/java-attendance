package domain;

import constants.DateConstants;
import exception.DuplicateAttendanceException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class AttendanceBook {
    private final Map<Integer, Attendance> attendances; // key: 몇 일, value: 출석 시간

    public AttendanceBook() {
        this.attendances = new HashMap<>();
    }

//    public String getStatusAt(int date) {
//        Attendance attendance = attendances.get(date);
//        return attendance.getStatus();
//    }

    public Attendance create(int date, int hour, int minute) {
        if (attendances.containsKey(date)) {
            throw new DuplicateAttendanceException();
        }
        Attendance attendance = new Attendance(
                LocalDateTime.of(DateConstants.YEAR, DateConstants.MONTH, date, hour, minute)
        );
        attendances.put(date, attendance);
        return attendance;
    }

    public Optional<Attendance> findAttendanceByDate(int date) {
        if (attendances.containsKey(date)) {
            return Optional.of(attendances.get(date));
        }
        return Optional.empty();
    }

    public void replace(Attendance beforeAttendance, Attendance afterAttendance) {
        int date = beforeAttendance.getTime().getDayOfMonth();
        attendances.replace(date, beforeAttendance, afterAttendance);
    }
}
