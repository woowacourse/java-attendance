package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        return attendTime.checkTime().getType();
    }

    public Optional<AttendTime> findAttendanceByDate(int date) {
        return attendanceHistory.getAttendTimes().stream()
                .filter(attendTime -> attendTime.getAttendTime().getDayOfMonth() == date)
                .findAny();
    }

    public void deleteAttendance(int date) {
        List<AttendTime> attendTimes = attendanceHistory.getAttendTimes();
        for (int i = 0; i < attendTimes.size(); i++) {
            if (attendTimes.get(i).getAttendTime().getDayOfMonth() == date) {
                attendTimes.remove(i);
            }
        }
    }

    public String getName() {
        return name;
    }

    public List<AttendTime> getAttendTimes() {
        return attendanceHistory.getAttendTimes();
    }

    public AttendanceHistory getAttendanceHistory() {
        return attendanceHistory;
    }

    public boolean checkNickName(String nickname) {
        return this.name.equals(nickname);
    }
}
