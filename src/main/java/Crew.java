import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Crew {
    private final String name;
    private final Map<LocalDate, LocalTime> dailyAttendances;

    private Crew(String name) {
        this.name = name;
        this.dailyAttendances = new HashMap<>();
    }

    public static Crew createByName(String name) {
        return new Crew(name);
    }

    public void addDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        dailyAttendances.putAll(dateAndTime);
    }

    public boolean hasName(String value) {
        return Objects.equals(name, value);
    }

    public String getName() {
        return name;
    }
}