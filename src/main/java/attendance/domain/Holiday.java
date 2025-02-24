package attendance.domain;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.time.DayOfWeek.SATURDAY;
import static java.time.DayOfWeek.SUNDAY;

public class Holiday {

    private final List<LocalDate> holidays = new ArrayList<>();

    public void addHoliday(LocalDate holiday) {
        if (holidays.contains(holiday)) {
            throw new IllegalArgumentException("[ERROR] 이미 추가된 휴일입니다.");
        }
        holidays.add(holiday);
    }

    public boolean isHoliday(LocalDate date) {
        return date.getDayOfWeek() == SATURDAY || date.getDayOfWeek() == SUNDAY || holidays.contains(date);
    }

    public void validateHoliday(LocalDate date) {
        if (isHoliday(date)) {
            String formatted = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                    date.getMonthValue(),
                    date.getDayOfMonth(),
                    date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
            );
            throw new IllegalArgumentException(formatted);
        }
    }
}
