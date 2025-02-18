import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Crew {
    private final String name;
    private final List<DailyAttendance> dailyAttendances;

    private Crew(String name) {
        this.name = name;
        this.dailyAttendances = new ArrayList<>();
    }

    public static Crew createByName(String name) {
        return new Crew(name);
    }

    public void addDailyAttendance(String date, String time) {
        dailyAttendances.add(new DailyAttendance(date, time));
    }

    public boolean hasName(String value) {
        return Objects.equals(name, value);
    }

    public String getName() {
        return name;
    }

    public DailyAttendance getDailyAttendanceByDate(String date) {
        return dailyAttendances.stream()
                .filter(dailyAttendance -> dailyAttendance.hasDate(date))
                .findFirst()
                .orElseThrow();
    }

}
