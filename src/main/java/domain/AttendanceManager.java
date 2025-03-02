package domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AttendanceManager {
    private final Map<NickName, Attendances> attendanceManager;

    public AttendanceManager() {
        this.attendanceManager = new HashMap<>();
    }

    public void attend(NickName nickName, AttendanceRecord attendanceRecord) {
        Attendances attendances = attendanceManager.getOrDefault(nickName, new Attendances());
        attendances.attend(attendanceRecord);
        attendanceManager.put(nickName, attendances);
    }

    public boolean isAttended(NickName nickName, AttendanceRecord checkAttendanceRecord) {
        Attendances attendances = attendanceManager.getOrDefault(nickName, new Attendances());
        return attendances.isAttended(checkAttendanceRecord);
    }

    public void edit(NickName nickName, AttendanceRecord editAttendanceRecord) {
        Attendances attendances = attendanceManager.getOrDefault(nickName, new Attendances());
        attendances.edit(editAttendanceRecord);
        attendanceManager.put(nickName, attendances);
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
