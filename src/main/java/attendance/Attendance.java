package attendance;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class Attendance {

    private final LocalDate attendDate;

    public Attendance(LocalDate attendDate) {
        validateAttendDate(attendDate);
        this.attendDate = attendDate;
    }

    private void validateAttendDate(LocalDate attendDate) {
        DayOfWeek dayOfWeekOfAttendDate = attendDate.getDayOfWeek();
        if (dayOfWeekOfAttendDate == DayOfWeek.SATURDAY || dayOfWeekOfAttendDate == DayOfWeek.SUNDAY) {
            throw new IllegalArgumentException("[ERROR] %d월 %d일 %s은(는) 등교일이 아닙니다"
                    .formatted(attendDate.getMonthValue(), attendDate.getDayOfMonth(),
                            attendDate.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREAN)));
        }
    }
}
