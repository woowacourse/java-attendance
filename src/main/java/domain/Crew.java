package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class Crew {

    private final String nickname;
    private final Map<LocalDate, AttendanceTime> attendanceTimes = new HashMap<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public AttendanceStatus insertAttendanceTime(LocalDate date, LocalTime time) {
        if (attendanceTimeExists(date)) {
            throw new IllegalArgumentException(date + ": 이미 출석 기록이 존재합니다. 수정 기능을 이용해주세요.");
        }
        AttendanceStatus status = AttendanceStatus.of(date, time);
        attendanceTimes.put(date, new AttendanceTime(time, status));
        return status;
    }

    public void modifyAttendanceTime(LocalDate date, LocalTime time) {
        // TODO: 같은 time으로 수정하려고 하는 경우 처리
        attendanceTimes.put(date, new AttendanceTime(time, AttendanceStatus.of(date, time)));
    }

    public AttendanceTime getAttendanceTimeByDate(LocalDate date) {
        if (!attendanceTimeExists(date)) {
            return new AttendanceTime(null, AttendanceStatus.ABSENT);
        }
        return attendanceTimes.get(date);
    }

    public boolean attendanceTimeExists(LocalDate date) {
        return attendanceTimes.containsKey(date);
    }

    public AttendanceStatus getAttendanceStatusByDate(LocalDate date) {
        if (!attendanceTimeExists(date)) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.of(date, attendanceTimes.get(date).time());
    }

    public String getNickname() {
        return nickname;
    }
}
