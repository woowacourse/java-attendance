package domain.constant;

import domain.Day;

import java.time.LocalDate;

public class StandardDate {

    public static final Day TODAY = new Day(LocalDate.of(2024, 12, 26));

    private StandardDate() {
    }
}
