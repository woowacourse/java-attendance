package domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Crew {

    private final String name;
    private final AttendanceHistory attendanceHistory;
//    private final List<AttendTime> attendTimes;

    public Crew(String nickname, String attendTime) {
        this.name = nickname;
        this.attendanceHistory = new AttendanceHistory(new ArrayList<>());
        attendanceHistory.addAttendance(new AttendTime(attendTime));
    }

    public void addAttendTime(String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
        attendanceHistory.addAttendance(attendTime);
    }

    public String attend(final String inputTime) {
        AttendTime attendTime = new AttendTime(inputTime);
//        attendTimes.add(attendTime);
        return attendTime.checkTime();
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

    public AttendTime findAttendanceByDate(final int date) {
        return attendanceHistory.getAttendTimes().stream()
                .filter(attendTime -> attendTime.getAttendTime().getDayOfMonth() == date)
                .findAny()
                .orElse(null);
    }

    public void deleteAttendance(final int date) {
        List<AttendTime> attendTimes = attendanceHistory.getAttendTimes();
        IntStream.range(0, attendTimes.size())
                .forEach(i -> {
                    if (attendTimes.get(i).getAttendTime().getDayOfMonth() == date) {
                        attendTimes.remove(i);
                    }
                });
    }
}
