package domain;

import java.util.List;

public class AttendanceHistory {
    private List<AttendTime> attendTimes;
    private AttendanceStatus attendanceStatus;

    public AttendanceHistory(List<AttendTime> attendTimes) {
        this.attendTimes = attendTimes;
    }

    public void addAttendance(AttendTime attendTime) {
        attendTimes.add(attendTime);
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }

    public int calculateOnTime() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkTime().equals("출석")) {
                total += 1;
            }
        }
        return total;
    }

    public int calculateLate(){
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkTime().equals("지각")) {
                total += 1;
            }
        }
        return total;
    }

    public int calculateAbsent() {
        int total = 0;

        for (AttendTime attendTime : attendTimes) {

            if (attendTime.checkTime().equals("결석")) {
                total += 1;
            }
        }
        total += 21 - attendTimes.size();

        return total;
    }

    public AttendanceStatus getAttendanceStatus() {
        attendanceStatus = new AttendanceStatus(calculateOnTime(),calculateLate(),calculateAbsent());
        return attendanceStatus;
    }
}
