package model.exception;

import common.DateTimeFormat;
import java.time.LocalDate;

public class HolidayAttendanceException extends IllegalArgumentException{
    public HolidayAttendanceException(LocalDate date) {
        super(String.format("%s은 등교일이 아닙니다.", date.format(DateTimeFormat.MONTH_DATE_DAY_FORMATTER)));
    }
}
