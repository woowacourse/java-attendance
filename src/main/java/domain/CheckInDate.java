package domain;

import java.time.LocalDate;

public class CheckInDate {
    private final LocalDate checkInDate;

    private CheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public static CheckInDate of(LocalDate checkInDate) {
        return new CheckInDate(checkInDate);
    }
}
