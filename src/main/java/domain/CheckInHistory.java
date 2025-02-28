package domain;

import exception.AppException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.TreeMap;

public class CheckInHistory {
    private final TreeMap<CheckInDate, CheckInTime> history;

    private CheckInHistory(TreeMap<CheckInDate, CheckInTime> history) {
        this.history = history;
    }

    public static CheckInHistory of(TreeMap<CheckInDate, CheckInTime> history) {
        return new CheckInHistory(history);
    }

    public void checkIn(CheckInDate checkInDate, CheckInTime checkInTime) {
        validateAlreadyCheckIn(checkInDate);
        history.put(checkInDate, checkInTime);
    }

    public int getCheckInCount() {
        return history.size();
    }

    public void modifyCheckInTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        validateNonSameTime(checkInDate, checkInTime);
        history.put(checkInDate, checkInTime);
    }

    private void validateNonSameTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        if (history.get(checkInDate).equals(checkInTime)) {
            throw new AppException("이미 같은 시간에 출석 기록이 있습니다.");
        }
    }

    private void validateAlreadyCheckIn(CheckInDate checkInDate) {
        if (history.containsKey(checkInDate)) {
            throw new AppException("이미 출석 기록이 있습니다. 수정 기능을 이용해 주세요.");
        }
    }

    public int countPresence(LocalDate now) {
        int presenceCount = 0;
        for (CheckInDate checkInDate : history.keySet()) {
            if (checkInDate.getCheckInDate().isBefore(now)) {
                LocalTime classStartTime = ClassTime.getClassStartTime(checkInDate.getCheckInDate());
                CheckInTime checkInTime = history.get(checkInDate);
                AttendanceStatus status = AttendanceStatus.determineAttendanceStatus(classStartTime, checkInTime.getCheckInTime());
                if (status == AttendanceStatus.PRESENCE)
                    presenceCount++;
            }
        }
        return presenceCount;
    }

    public int getLateCount(LocalDate now) {
        int lateCount = 0;
        for (CheckInDate checkInDate : history.keySet()) {
            if (checkInDate.getCheckInDate().isBefore(now)) {
                LocalTime classStartTime = ClassTime.getClassStartTime(checkInDate.getCheckInDate());
                CheckInTime checkInTime = history.get(checkInDate);
                AttendanceStatus status = AttendanceStatus.determineAttendanceStatus(classStartTime, checkInTime.getCheckInTime());
                if (status == AttendanceStatus.LATE)
                    lateCount++;
            }
        }
        return lateCount;
    }
}
