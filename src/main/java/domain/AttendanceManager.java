package domain;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import util.DateUtil;

public class AttendanceManager {
    private final Map<NickName, Attendances> attendanceManager;

    private final LocalTime START_TIME = LocalTime.of(8, 0);
    private final LocalTime END_TIME = LocalTime.of(23, 0);

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
        validateAttendAbleTime(attendanceRecord);
        Attendances attendances = attendanceManager.get(nickName);
        try {
            attendances.attend(attendanceRecord);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("이미 출석한 닉네임입니다. 수정 기능을 사용해 주세요.");
        }
    }

    public void edit(NickName nickName, AttendanceRecord editAttendanceRecord) {
        validateNameExist(nickName);
        validateAttendAbleDate(editAttendanceRecord);
        validateAttendAbleTime(editAttendanceRecord);
        Attendances attendances = attendanceManager.get(nickName);
        attendances.edit(editAttendanceRecord);
    }

    public AttendanceRecord getAttendanceRecordOfSameDate(NickName nickName, AttendanceRecord editAttendanceRecord) {
        validateNameExist(nickName);
        validateAttendAbleDate(editAttendanceRecord);
        validateAttendAbleTime(editAttendanceRecord);
        Attendances attendances = attendanceManager.get(nickName);
        return attendances.getAttendanceRecordOfSameDate(editAttendanceRecord);
    }

    public Attendances checkAttendance(NickName nickName, List<Integer> checkingDates) {
        validateNameExist(nickName);
        Attendances attendances = attendanceManager.get(nickName);
        List<AttendanceRecord> attendanceRecords = checkingDates.stream()
                .map(attendances::getAttendanceRecordOfSameDate)
                .toList();
        return new Attendances(attendanceRecords);
    }

    private void validateNameExist(NickName nickName) {
        if (!isRegistered(nickName)) {
            throw new IllegalArgumentException("등록되지 않은 닉네임입니다.");
        }
    }

    public boolean isRegistered(NickName nickName) {
        return attendanceManager.containsKey(nickName);
    }

    private void validateAttendAbleDate(AttendanceRecord attendanceRecord) {
        if (!DateUtil.isAttendAbleDate(attendanceRecord.getDate()
                .getDayOfMonth())) {
            throw new IllegalArgumentException("등교일이 아닙니다.");
        }
    }

    private void validateAttendAbleTime(AttendanceRecord attendanceRecord) {
        LocalTime time = attendanceRecord.getTime();
        if (time.isBefore(START_TIME) || time.isAfter(END_TIME)) {
            throw new IllegalArgumentException("등교 시간이 아닙니다.");
        }
    }

    public WarningCrews findWarningCrews(List<Integer> checkingDates) {
        return attendanceManager.keySet()
                .stream()
                .filter(attendances -> WarningStatus.calculateWarningStatus(
                        checkAttendance(attendances, checkingDates)
                                .countAttendanceStatus()) != WarningStatus.CLEAR)
                .map(attendances -> new WarningCrew(attendances, checkAttendance(attendances, checkingDates)
                        .countAttendanceStatus()))
                .collect(Collectors.collectingAndThen(Collectors.toList(), WarningCrews::new));
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
