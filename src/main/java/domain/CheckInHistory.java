package domain;

import exception.AppException;

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
}
