import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {

    public static LocalDateTime parseDatetime(String day, String time) {
        try {
            final int parsedDay = Integer.parseInt(day);
            vaildateDay(parsedDay);
            var hour = time.substring(0, 2);
            var min = time.substring(3);
            return LocalDateTime.of(2024, 12, parsedDay, Integer.parseInt(hour), Integer.parseInt(min));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능", e);
        }
    }

    public static LocalDateTime parseDatetime(String time) {
        var hour = time.substring(0, 2);
        var min = time.substring(3);
        return LocalDateTime.of(2024, 12, 1, Integer.parseInt(hour), Integer.parseInt(min));
    }

    public static boolean isDayEqual(final int day, final LocalDateTime datetime) {
        return datetime.getDayOfMonth() == day;
    }

    private static void vaildateDay(final int day) {
        if (day < 1 || day > 31) {
            throw new IllegalArgumentException("1-31만 가능");
        }
    }
}
