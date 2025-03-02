package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AttendanceManager {
    private final Map<NickName, Attendances> attendanceManager;

    public AttendanceManager() {
        this.attendanceManager = new HashMap<>();
    }

    public void attend(NickName name, String time) {
        Attendances attendances = attendanceManager.getOrDefault(name, new Attendances());
        attendances.attend(time);
        attendanceManager.put(name, attendances);
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
        return Objects.equals(attendanceManager, that.attendanceManager);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(attendanceManager);
    }
}
