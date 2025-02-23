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
        return attendTime.checkTime();
    }

    public AttendTime findAttendanceByDate(int date) {
        return attendanceHistory.getAttendTimes().stream()
                .filter(attendTime -> attendTime.getAttendTime().getDayOfMonth() == date)
                .findAny()
                .orElse(null);
    }

    public void deleteAttendance(int date) {
        List<AttendTime> attendTimes = attendanceHistory.getAttendTimes();
        for (int i = 0; i < attendTimes.size(); i++) {
            if (attendTimes.get(i).getAttendTime().getDayOfMonth() == date) {
                attendTimes.remove(i);
            }
        }
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

    public String getName() {
        return name;
    }
}
