package domain;

import java.time.LocalDate;

public class DecemberCalender {

    private final int year = 2024;
    private final int month = 12;
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
        return LocalDate.of(year, month, day);
    }
}
