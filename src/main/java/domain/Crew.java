package domain;

import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String name;
    private final AttendanceHistory attendanceHistory;

    public Crew(String nickname, String attendTime) {
        this.name = nickname;
        this.attendanceHistory = new AttendanceHistory(new ArrayList<>());
        attendanceHistory.addAttendance(new AttendTime(attendTime));
    }

    public void addAttendTime(String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
        attendanceHistory.addAttendance(attendTime);
    }

    public String attend(String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
        attendanceHistory.getAttendanceStatus();
        return attendTime.checkAttendanceStatus();
    }

    public AttendTime findAttendTimeByDate(int date) {
        return attendanceHistory.findAttendTimeByDate(date);
    }

    public List<AttendTime> getAttendTimes() {
        return attendanceHistory.getAttendTimes();
    }

    public AttendanceHistory getAttendanceHistory() {
        return attendanceHistory;
    }

    public boolean isSameType(final String type) {
        return attendanceHistory.getAttendanceStatus().getStatus().equals(type);
    }

    public boolean isSameName(final String name) {
        return this.name.equals(name);
    }

    public String getName() {
        return name;
    }

    public boolean isAlreadyAttend(int date) {
        return attendanceHistory.isAlreadyAttend(date);
    }
}
