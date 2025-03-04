package controller;

import java.time.LocalDate;
import model.AttendanceDate;

public class TodayDateGenerator {

    public AttendanceDate generate() {
        return new AttendanceDate(LocalDate.of(2024, 12, 12));
    }

}
