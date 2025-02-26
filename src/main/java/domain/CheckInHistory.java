package domain;

import java.util.TreeSet;

public class CheckInHistory {
    private final TreeSet<CheckInTime> history;

    private CheckInHistory(TreeSet<CheckInTime> history) {
        this.history = history;
    }

    public static CheckInHistory of(TreeSet<CheckInTime> history) {
        return new CheckInHistory(history);
    }

    public void checkIn(CheckInTime checkInTime) {
        history.add(checkInTime);
    }

    public int getCheckInCount() {
        return history.size();
    }
}
