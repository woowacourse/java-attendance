package domain;

import static domain.AttendTime.ABSENT;
import static domain.AttendTime.ATTENDED;
import static domain.AttendTime.LATE;

import java.util.List;

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
            if (attendTime.checkTime().equals(ATTENDED)) {
                total += 1;
            }
        }
        return total;
    }

    public int calculateLate() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkTime().equals(LATE)) {
                total += 1;
            }
        }
        return total;
    }

    public int calculateAbsent() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkTime().equals(ABSENT)) {
                total += 1;
            }
        }
        total += 21 - attendTimes.size();

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
