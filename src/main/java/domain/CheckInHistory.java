package domain;

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
        history.add(checkInDateTime);
    }

    public int getCheckInCount() {
        return history.size();
    }
}
