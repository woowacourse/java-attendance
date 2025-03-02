package domain;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceManager {
    private final Map<NickName, Attendances> attendanceManager;

    public AttendanceManager() {
        this.attendanceManager = new HashMap<>();
    }

    public void attend(NickName nickName, LocalTime attendingTime) {
        Attendances attendances = attendanceManager.getOrDefault(nickName, new Attendances());
        attendances.attend(attendingTime);
        attendanceManager.put(nickName, attendances);
    }

    public boolean isAttended(NickName nickName) {
        Attendances attendances = attendanceManager.getOrDefault(nickName, new Attendances());
        return attendances.isAttended();
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
