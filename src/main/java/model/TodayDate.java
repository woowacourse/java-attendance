package model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TodayDate {

    private final LocalDate todayDate;

    public TodayDate(LocalDate todayDate) {
        this.todayDate = todayDate;
    }

    public LocalDate getTodayDate() {
        return todayDate;
    }

    public LocalDateTime makeLocalDateToLocalDateTime(String time) {
        try {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(time,dateTimeFormatter);
            return todayDate.atTime(localTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("[ERROR] 시간 형식에 맞지 않습니다.");
        }

    }

}
