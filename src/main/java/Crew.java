import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
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
        LocalDate date = dateAndTime.keySet().stream()
                .findAny()
                .orElseThrow();

        validateIsDateUnique(date);

        dailyAttendances.putAll(dateAndTime);
    }

    private void validateIsDateUnique(LocalDate date) {
        if (dailyAttendances.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석한 날짜입니다. 수정 기능을 이용해주세요.");
        }
    }

    public boolean hasName(String value) {
        return Objects.equals(name, value);
    }

    public String getName() {
        return name;
    }

    public void modifyDailyAttendance(Map<LocalDate, LocalTime> dateAndTime) {
        LocalDate date = dateAndTime.keySet().stream()
                .findAny()
                .orElseThrow(()-> new IllegalArgumentException("[ERROR] "));

        dailyAttendances.putAll(dateAndTime);
    }
}