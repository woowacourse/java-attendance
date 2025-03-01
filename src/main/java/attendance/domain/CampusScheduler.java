package attendance.domain;

import attendance.view.TimeFormatter;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class CampusScheduler {

    public void validateOperationDate(final LocalDate attendanceDate) {
        if (isNotOperationDate(attendanceDate)) {
            throw new IllegalArgumentException(
                    "[ERROR] " + TimeFormatter.makeDateMessage(attendanceDate) + "은 등교일이 아닙니다.");
        }
    }

    private boolean isNotOperationDate(final LocalDate attendanceDate) {
        return isWeekend(attendanceDate) || isHoliday(attendanceDate);
    }

    private boolean isWeekend(final LocalDate attendanceDate) {
        DayOfWeek dayOfWeek = attendanceDate.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    private boolean isHoliday(final LocalDate attendanceDate) {
        return Holiday.isHoliday(attendanceDate.getMonthValue(), attendanceDate.getDayOfMonth());
    }
}
