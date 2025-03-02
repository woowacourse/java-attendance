package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceManager {
    private final Map<String, List<String>> attendances;

    public AttendanceManager() {
        this.attendances = new HashMap<>();
    }

    public void attend(String name, String time) {
        attendances.put(name, List.of(time));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AttendanceManager that = (AttendanceManager) o;
        return Objects.equals(attendances, that.attendances);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendances);
    }
}
