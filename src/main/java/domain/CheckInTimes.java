package domain;

import java.util.List;

import static domain.AttendanceStatus.*;

public class CheckInTimes {
    private final List<CheckInTime> checkInTimes;

    private CheckInTimes(List<CheckInTime> checkInTimes) {
        this.checkInTimes = checkInTimes;
    }

    public static CheckInTimes of (List<CheckInTime> checkInTimes) {
        return new CheckInTimes(checkInTimes);
    }

    public int countPresence() {
        return Math.toIntExact(
                checkInTimes.stream()
                .filter(time -> time.getAttendanceStatus() == PRESENCE)
                .count()
        );
    }

    public int countLate() {
        return Math.toIntExact(
                checkInTimes.stream()
                        .filter(time -> time.getAttendanceStatus() == LATE)
                        .count()
        );
    }

    public int countAbsence() {
        return Math.toIntExact(
                checkInTimes.stream()
                        .filter(time -> time.getAttendanceStatus() == ABSENCE)
                        .count()
        );
    }
}
