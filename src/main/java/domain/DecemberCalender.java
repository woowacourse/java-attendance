package domain;

import java.time.LocalDate;

public class DecemberCalender {

    private static final int YEAR = 2024;
    private static final int MONTH = 12;

    private final int day;

    public DecemberCalender(int day) {
        validateDay(day);
        this.day = day;
    }

    private void validateDay(int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("[ERROR] 유효한 날짜가 아닙니다.");
        }
    }

    public LocalDate getDate() {
        return LocalDate.of(YEAR, MONTH, day);
    }
}
