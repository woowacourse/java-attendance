package domain;

import java.time.LocalDateTime;

public class Crew {

    private final String nickname;
    private final AttendanceRecord attendanceRecord;

    public Crew(String nickname, LocalDateTime attendanceTime, DateProvider dateProvider) {
        this.nickname = nickname;
        this.attendanceRecord = new AttendanceRecord(dateProvider);
        addAttendanceTime(attendanceTime);
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDateTime attend(LocalDateTime now) {
        return attendanceRecord.attend(now);
    }

    public void addAttendanceTime(LocalDateTime attendanceTime) {
        attendanceRecord.add(attendanceTime);
    }

    public AttendanceRecord getAttendanceRecord() {
        return attendanceRecord;
    }

    public AttendanceStatus getAttendanceStatus(int dayOfMonth) {
        return attendanceRecord.getAttendanceStatus(dayOfMonth);
    }

}
