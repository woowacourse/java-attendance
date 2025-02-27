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
        validateDuplicate(attendTime);
        attendTimes.add(attendTime);
    }

    public void validateDuplicate(AttendTime attendTime) {
        attendTimes.stream()
                .filter(a -> a.getDayOfMonth() == attendTime.getDayOfMonth())
                .findAny()
                .ifPresent(a -> {
                    throw new IllegalArgumentException("[ERROR] 이미 출석 기록이 존재하는 날짜입니다. 수정 기능을 이용해주세요.");
                });
    }

    public AttendTime findAttendTimeByDate(final int date) {
        return attendTimes.stream()
                .filter(attendTime -> attendTime.getDayOfMonth() == date)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 크루 출석 기록에 존재하지 않는 날짜입니다."));
    }

    public int calculateAttended() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            total += addCount(attendTime, ATTENDED);
        }
        return total;
    }

    public int calculateLate() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            total += addCount(attendTime, LATE);
        }
        return total;
    }

    public int calculateAbsent() {
        int total = 0;
        for (AttendTime attendTime : attendTimes) {
            total += addCount(attendTime, ABSENT);
        }
        total += 21 - attendTimes.size();
        return total;
    }

    private static int addCount(AttendTime attendTime, String status) {
        if (attendTime.checkAttendanceStatus().equals(status)) {
            return 1;
        }
        return 0;
    }

    public DangerousStatus getAttendanceStatus() {
        dangerousStatus = new DangerousStatus(calculateAttended(), calculateLate(), calculateAbsent());
        return dangerousStatus;
    }

    public List<AttendTime> getAttendTimes() {
        return attendTimes;
    }

    public boolean isAlreadyAttend(int date) {
        return attendTimes.stream()
                .anyMatch(attendTime -> attendTime.getDayOfMonth() == date);
    }
}
