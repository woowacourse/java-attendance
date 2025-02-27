import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

public class AttendanceStorage {
    private Map<LocalDate, LocalTime> attendances;

    public AttendanceStorage() {
        this.attendances = new HashMap<>();
    }

    public void register(LocalDate date, LocalTime enterTime) {
        if (attendances.containsKey(date)) {
            throw new IllegalArgumentException("이미 존재하는 출석입니다.");
        }
        attendances.put(date, enterTime);
    }
}
