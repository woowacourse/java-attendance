package domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static domain.AttendanceStatus.*;

public class CheckInTimes {
    private List<CheckInTime> checkInTimes;

    private CheckInTimes(List<CheckInTime> checkInTimes) {
        this.checkInTimes = new ArrayList<>(checkInTimes);
    }

    public static CheckInTimes of(List<CheckInTime> checkInTimes) {
        return new CheckInTimes(checkInTimes);
    }

    public void add(CheckInTime checkInTime) {
        for (CheckInTime time : checkInTimes) {
            if (time.isSameDate(checkInTime)) {
                throw new IllegalArgumentException("[ERROR] CheckInTime is already in the list]");
            }
        }
        checkInTimes.add(checkInTime);
    }

    public void modify(CheckInTime checkInTime) {
        LocalDateTime now = LocalDateTime.now();
        if (checkInTime.isNotModifiable(now)) {
            throw new IllegalArgumentException("[ERROR] CheckInTime is not modifiable");
        }

        for (CheckInTime time : checkInTimes) {
            if (time.isSameDate(checkInTime)) {
                time.modify(checkInTime);
            }
        }
    }

    public List<CheckInTime> getAttendanceLog(LocalDateTime localDateTime) {
        List<CheckInTime> attendanceLog = new ArrayList<>();
        for (CheckInTime checkInTime : checkInTimes) {
            if (checkInTime.isBeforeDate(localDateTime)) {
                attendanceLog.add(checkInTime);
            }
        }

        return attendanceLog;
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
