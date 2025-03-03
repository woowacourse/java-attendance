package attendance.controller;

import java.time.LocalDate;

public class TodayGenerator implements DateGenerator{

    @Override
    public LocalDate generate() {
        return LocalDate.now();
    }
}
