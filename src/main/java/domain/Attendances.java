package domain;

import java.util.HashMap;
import java.util.Map;

public class Attendances {

    private final Map<AttendanceDate, AttendanceTime> dateToTime;

    private Attendances(Map<AttendanceDate, AttendanceTime> dateToTime) {
        this.dateToTime = dateToTime;
    }

    public static Attendances initialize() {
        return new Attendances(new HashMap<>());
    }

    public Attendance add(Attendance attendance) {
        dateToTime.put(attendance.getAttendanceDate(), attendance.getAttendanceTime());
        return attendance;
    }

    public boolean existsByDate(AttendanceDate attendanceDate) {
        return dateToTime.containsKey(attendanceDate);
    }

    public Attendance findByDate(AttendanceDate attendanceDate) {
        if (existsByDate(attendanceDate)) {
            AttendanceTime attendanceTime = dateToTime.get(attendanceDate);
            return Attendance.of(attendanceDate, attendanceTime);
        }
        throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
    }
}
