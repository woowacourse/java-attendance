package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import util.DateUtil;

public class AttendanceManager {
    private final Map<NickName, Attendances> attendanceManager;

    public AttendanceManager() {
        this.attendanceManager = new HashMap<>();
    }

    public void register(NickName nickName) {
        if (isRegistered(nickName)) {
            return;
        }
        attendanceManager.put(nickName, new Attendances());
    }

    public void attend(NickName nickName, AttendanceRecord attendanceRecord) {
        validateNameExist(nickName);
        validateAttendAbleDate(attendanceRecord);
        Attendances attendances = attendanceManager.get(nickName);
        attendances.attend(attendanceRecord);
    }

    public boolean isAttended(NickName nickName, AttendanceRecord checkAttendanceRecord) {
        validateNameExist(nickName);
        validateAttendAbleDate(checkAttendanceRecord);
        Attendances attendances = attendanceManager.get(nickName);
        return attendances.isAttended(checkAttendanceRecord);
    }

    public void edit(NickName nickName, AttendanceRecord editAttendanceRecord) {
        validateNameExist(nickName);
        validateAttendAbleDate(editAttendanceRecord);
        Attendances attendances = attendanceManager.get(nickName);
        attendances.edit(editAttendanceRecord);
    }

    public Attendances checkAttendance(NickName nickName, List<Integer> checkingDates) {
        validateNameExist(nickName);
        Attendances attendances = new Attendances();
        attendances.attend(AttendanceRecord.of("09", "10:00"));
        attendances.attend(AttendanceRecord.of("10", "10:00"));
        return attendances;
    }

    private void validateNameExist(NickName nickName) {
        if (!isRegistered(nickName)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    private boolean isRegistered(NickName nickName) {
        return attendanceManager.containsKey(nickName);
    }

    private void validateAttendAbleDate(AttendanceRecord attendanceRecord) {
        if (!DateUtil.isAttendAbleDate(attendanceRecord.getDate()
                .getDayOfMonth())) {
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
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

    @Override
    public String toString() {
        return "AttendanceManager{" + "attendanceManager=" + attendanceManager + '}';
    }
}
