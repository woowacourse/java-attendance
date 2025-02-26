package domain;

import exception.AppException;

import java.util.TreeSet;

public class CheckInHistory {
    private final TreeSet<CheckInDateTime> history;

    private CheckInHistory(TreeSet<CheckInDateTime> history) {
        this.history = history;
    }

    public static CheckInHistory of(TreeSet<CheckInDateTime> history) {
        return new CheckInHistory(history);
    }

    public void checkIn(CheckInDateTime checkInDateTime) {
        validateAlreadyCheckIn(checkInDateTime);
        history.add(checkInDateTime);
    }

    public int getCheckInCount() {
        return history.size();
    }

    private void validateAlreadyCheckIn(CheckInDateTime checkInDateTime) {
        if (history.contains(checkInDateTime)) {
            throw new AppException("이미 출석 기록이 있습니다. 수정 기능을 이용해 주세요.");
        }
    }
}
