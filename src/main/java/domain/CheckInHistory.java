package domain;

import exception.AppException;

import java.time.DayOfWeek;
import java.time.LocalDate;
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
        return countSomeStatus(now, AttendanceStatus.PRESENCE);
    }

    public int countLate(LocalDate now) {
        return countSomeStatus(now, AttendanceStatus.LATE);
    }

    public int countAbsence(LocalDate now) {
        return countTotal(now) - (countPresence(now) + countLate(now));
    }

    public int countSomeStatus(LocalDate now, AttendanceStatus status) {
        return (int) history.entrySet().stream()
                .filter(entry -> entry.getKey().getCheckInDate().isBefore(now))
                .map(entry -> AttendanceStatus.determineAttendanceStatus(
                        ClassTime.getClassStartTime(entry.getKey().getCheckInDate()),
                        entry.getValue().getCheckInTime()
                ))
                .filter(determinedStatus -> determinedStatus == status)
                .count();
    }

    public int countTotal(LocalDate now) {
        int totalCount = now.getDayOfMonth() - 1;
        for (int i = 1; i < now.getDayOfMonth(); i++) {
            LocalDate date = LocalDate.of(now.getYear(), now.getMonth(), i);
            if (Holidays.isHoliday(date) || date.getDayOfWeek() == DayOfWeek.SATURDAY
                    || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                totalCount--;
            }
        }
        return totalCount;
    }

}
