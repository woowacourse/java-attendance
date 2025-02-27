package domain;

public class CheckInDateTime implements Comparable<CheckInDateTime> {
    private final CheckInDate checkInDate;
    private final CheckInTime checkInTime;

    private CheckInDateTime(CheckInDate checkInDate, CheckInTime checkInTime) {
        this.checkInDate = checkInDate;
        this.checkInTime = checkInTime;
    }

    public static CheckInDateTime of(CheckInDate checkInDate, CheckInTime checkInTime) {
        return new CheckInDateTime(checkInDate, checkInTime);
    }

    @Override
    public int compareTo(CheckInDateTime o) {
        return this.checkInDate.compareTo(o.checkInDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckInDateTime that = (CheckInDateTime) o;
        return checkInDate.equals(that.checkInDate);
    }

    @Override
    public int hashCode() {
        return checkInDate.hashCode();
    }
}
