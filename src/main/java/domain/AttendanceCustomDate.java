package domain;

import constants.DateConstants;

import java.time.LocalDateTime;

public class AttendanceCustomDate {
    public static LocalDateTime now() {
        LocalDateTime now = LocalDateTime.now();
        return now.withYear(DateConstants.YEAR).withMonth(DateConstants.MONTH.getValue()).withDayOfMonth(13);
    }
}
