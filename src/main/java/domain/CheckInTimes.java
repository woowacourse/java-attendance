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
        validateAddable(checkInTime);
        checkInTimes.add(checkInTime);
    }

    public void modify(CheckInTime checkInTime) {
        validateModifiable(checkInTime);

        checkInTimes.stream()
                .filter(time -> time.isSameDate(checkInTime))
                .forEach(time -> time.modify(checkInTime));
    }

    public List<CheckInTime> getAttendanceLog(LocalDateTime localDateTime) {
        List<CheckInTime> attendanceLog = new ArrayList<>();

        checkInTimes.stream()
                .filter(time -> time.isBeforeDate(localDateTime))
                .forEach(attendanceLog::add);

        return attendanceLog;
    }

    private void validateAddable(CheckInTime checkInTime) {

        checkInTimes.stream()
                .filter(time -> time.isSameDate(checkInTime))
                .findAny()
                .ifPresent(time -> {
                    throw new IllegalArgumentException("[ERROR] CheckInTime is already in the list]");
                });
    }

    private void validateModifiable(CheckInTime checkInTime) {
        LocalDateTime now = LocalDateTime.now();
        if (checkInTime.isNotModifiable(now)) {
            throw new IllegalArgumentException("[ERROR] CheckInTime is not modifiable");
        }
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
