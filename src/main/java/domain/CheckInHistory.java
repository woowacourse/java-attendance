package domain;

import exception.AppException;

import java.time.LocalDate;
import java.util.TreeMap;
import java.util.stream.IntStream;

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

    public CheckInTime modifyCheckInTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        validateNonSameTime(checkInDate, checkInTime);
        CheckInTime beforeCheckInTime = history.get(checkInDate);
        history.put(checkInDate, checkInTime);
        return beforeCheckInTime;
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

    private int countSomeStatus(LocalDate now, AttendanceStatus status) {
        return (int) history.entrySet().stream()
                .filter(entry -> entry.getKey().getCheckInDate().isBefore(now))
                .map(entry -> AttendanceStatus.determineAttendanceStatus(
                        ClassTime.getClassStartTime(entry.getKey().getCheckInDate()),
                        entry.getValue().getCheckInTime()
                ))
                .filter(determinedStatus -> determinedStatus == status)
                .count();
    }

    private int countTotal(LocalDate now) {
        return (int) IntStream.range(1, now.getDayOfMonth())
                .mapToObj(day -> LocalDate.of(now.getYear(), now.getMonth(), day))
                .filter(date -> Holidays.isNotHoliday(date) && CheckInDate.isNotWeekend(date))
                .count();
    }

    public PenaltyStatus getPenaltyStatus(LocalDate now) {
        int late = countLate(now);
        int absence = countAbsence(now);
        return PenaltyStatus.determinePenalty(late, absence);
    }

    public boolean hasHistory(CheckInDate checkInDate) {
        return history.containsKey(checkInDate);
    }

    public CheckInTime getCheckInTime(CheckInDate checkInDate) {
        return history.get(checkInDate);
    }
}
