import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private final String nickname;
    private final List<LocalDateTime> attendanceHistory = new ArrayList<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public void attendance(LocalDate date, LocalTime time) {
        attendanceHistory.add(LocalDateTime.of(date, time));
    }

    public String getName() {
        return nickname;
    }
}
