package domain;

import static domain.AttendTime.ABSENT;
import static domain.AttendTime.ATTENDED;
import static domain.AttendTime.LATE;

import java.util.List;

public class AttendanceHistory {

    private final List<AttendTime> attendTimes;
    private DangerousStatus dangerousStatus;

    public AttendanceHistory(List<AttendTime> attendTimes) {
        this.attendTimes = attendTimes;
    }

    public void addAttendance(AttendTime attendTime) {
        attendTimes.add(attendTime);
    }

    public AttendTime findAttendTimeByDate(final int date) {
        return attendTimes.stream()
                .filter(attendTime -> attendTime.getDayOfMonth() == date)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 크루 출석 기록에 존재하지 않는 날짜입니다."));
    }

    public int calculateOnTime() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkAttendanceStatus().equals(ATTENDED)) {
                total += 1;
            }
        }
        return total;
    }

    public int calculateLate() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkAttendanceStatus().equals(LATE)) {
                total += 1;
            }
        }
        return total;
    }

    public int calculateAbsent() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            if (attendTime.checkAttendanceStatus().equals(ABSENT)) {
                total += 1;
            }
        }
        total += 21 - attendTimes.size();

        return total;
    }

    public DangerousStatus getAttendanceStatus() {
        dangerousStatus = new DangerousStatus(
                calculateOnTime(), calculateLate(), calculateAbsent());
        return dangerousStatus;
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }

    public void modifyAttendTime(final int date, final String time) {
        AttendTime attendTime = findAttendTimeByDate(date);
        attendTime.modifyAttendTime(time);
    }
}
