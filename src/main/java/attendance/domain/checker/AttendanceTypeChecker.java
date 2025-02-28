package attendance.domain.checker;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AttendanceTypeChecker {

    private final static LocalTime MONDAY_EDUCATION_START_TIME = LocalTime.of(13, 0, 0);
    private final static LocalTime NOT_MONDAY_EDUCATION_START_TIME = LocalTime.of(10, 0, 0);
    private final HolidayChecker holidayChecker;

    public AttendanceTypeChecker(HolidayChecker holidayChecker) {
        this.holidayChecker = holidayChecker;
    }

    public AttendanceType check(LocalDateTime arrivalDatetime) {
        holidayChecker.validateNotHoliday(arrivalDatetime.toLocalDate());
        CampusTime.validateCampusTime(arrivalDatetime.toLocalTime());
        if (arrivalDatetime.getDayOfWeek() == DayOfWeek.MONDAY) {
            return AttendanceType.parse(MONDAY_EDUCATION_START_TIME, arrivalDatetime.toLocalTime());
        }
        return AttendanceType.parse(NOT_MONDAY_EDUCATION_START_TIME, arrivalDatetime.toLocalTime());
    }

    public List<LocalDate> calculateNotHolidayInMonth(int year, Month month) {
        int lastDayInMonth = LocalDate.of(year, month.getValue(), 1).lengthOfMonth();
        return IntStream.range(1, lastDayInMonth + 1)
                .mapToObj(day -> LocalDate.of(year, month.getValue(), day))
                .filter(date -> !holidayChecker.checkHoliday(date))
                .collect(Collectors.toList());
    }
}
