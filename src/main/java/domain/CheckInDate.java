package domain;

import java.time.LocalDate;

public class CheckInDate implements Comparable<CheckInDate> {
    private final LocalDate checkInDate;

    private CheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public static CheckInDate of(LocalDate checkInDate) {
        return new CheckInDate(checkInDate);
    }

    @Override
    public int compareTo(CheckInDate o) {
        return checkInDate.compareTo(o.checkInDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInDate that = (CheckInDate) o;
        return checkInDate.equals(that.checkInDate);
    }

    @Override
    public int hashCode() {
        return checkInDate.hashCode();
    }
}
