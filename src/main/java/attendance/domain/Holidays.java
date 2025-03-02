package attendance.domain;

import attendance.util.FileUtil;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Holidays {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private final Set<LocalDate> holidays = new HashSet<>();

    public void initHoliday() {
        List<String> lineComponents = FileUtil.readFile("holidays.csv");

        lineComponents.stream()
                .map(line -> LocalDate.parse(line, DATE_FORMATTER))
                .forEach(holidays::add);
    }

    public void addHoliday(final LocalDate date) {
        holidays.add(date);
    }

    public boolean isNotHoliday(final LocalDate date) {
        return !isHoliday(date);
    }

    public void validateAttendanceDate(final LocalDate attendanceDate) {
        if (isHoliday(attendanceDate)) {
            throw new IllegalArgumentException(formatErrorMessage(attendanceDate));
        }
    }

    private boolean isHoliday(final LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY || holidays.contains(date);
    }

    private static String formatErrorMessage(final LocalDate date) {
        return String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
