package domain;

import java.util.List;

import static domain.AttendanceStatus.*;

public class CheckInTimes {
    private final List<CheckInTime> checkInTimes;

    private CheckInTimes(List<CheckInTime> checkInTimes) {
        this.checkInTimes = checkInTimes;
    }

    public static CheckInTimes of(List<CheckInTime> checkInTimes) {
        return new CheckInTimes(checkInTimes);
    }

    public int countPresence() {
        return countAttendenceStatus(PRESENCE);
    }

    public int countLate() {
        return countAttendenceStatus(LATE);
    }

    public int countAbsence() {
        return countAttendenceStatus(ABSENCE);
    }

    private int countAttendenceStatus(AttendanceStatus status) {
        return Math.toIntExact(
                checkInTimes.stream()
                        .filter(time -> time.getAttendanceStatus() == status)
                        .count()
        );
    }
}
