import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceTypeFactory {
    public static String getType(LocalDateTime attendAt) {
        DayOfWeek dayOfWeek = attendAt.getDayOfWeek();

        if (dayOfWeek.equals(DayOfWeek.MONDAY)) {
            return getTypeForMonday(attendAt);
        }

        if (attendAt.toLocalTime().isAfter(LocalTime.of(10, 30))) {
            return "ABSENCE";
        }

        if (attendAt.toLocalTime().isAfter(LocalTime.of(10, 5))) {
            return "LATE";
        }

        return "PRESENT";
    }

    private static String getTypeForMonday(LocalDateTime attendAt) {
        if (attendAt.toLocalTime().isAfter(LocalTime.of(13, 30))) {
            return "ABSENCE";
        }

        if (attendAt.toLocalTime().isAfter(LocalTime.of(13, 5))) {
            return "LATE";
        }

        return "PRESENT";
    }

}
