package domain;

import java.util.List;

import static domain.AttendanceType.*;

public class AttendanceHistory {

    private final List<AttendTime> attendTimes;
    private AttendanceStatus attendanceStatus;

    public AttendanceHistory(List<AttendTime> attendTimes) {
        this.attendTimes = attendTimes;
    }

    public void addAttendance(AttendTime attendTime) {
        attendTimes.add(attendTime);
    }

    public int calculateOnTime() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            total = getOnTimeTotal(attendTime, total);
        }
        return total;
    }

    private static int getOnTimeTotal(AttendTime attendTime, int total) {
        if (attendTime.checkTime().equals(ATTENDED)) {
            total += 1;
        }
        return total;
    }

    public int calculateLate() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            total = getLateTotal(attendTime, total);
        }
        return total;
    }

    private static int getLateTotal(AttendTime attendTime, int total) {
        if (attendTime.checkTime().equals(LATE)) {
            total += 1;
        }
        return total;
    }

    public int calculateAbsent() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            total = getAbsentTotal(attendTime, total);
        }
        total += December.DECEMBER_WEEKDAY_COUNTS - attendTimes.size();

        return total;
    }

    private static int getAbsentTotal(AttendTime attendTime, int total) {
        if (attendTime.checkTime().equals(ABSENT)) {
            total += 1;
        }
        return total;
    }

    public AttendanceStatus getAttendanceStatus() {
        attendanceStatus = new AttendanceStatus(
                calculateOnTime(), calculateLate(), calculateAbsent());
        return attendanceStatus;
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }
}
