package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CrewHistory {

    private final Map<Integer, LocalDateTime> attendance;

    public CrewHistory(final Map<Integer, LocalDateTime> attendance) {
        this.attendance = new TreeMap<>(attendance);
    }

    public void loadHistory(final LocalDateTime attendanceTime) {
        int day = attendanceTime.getDayOfMonth();
        attendance.put(day, attendanceTime);
    }

    public void attend(final LocalDateTime attendanceTime) {
        int day = attendanceTime.getDayOfMonth();
        if (attendance.containsKey(day)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
        attendance.put(day, attendanceTime);
    }

    public LocalDateTime modify(final LocalDateTime modifyDateTime, final LocalDate todayDate) {
        LocalDate modifyDate = LocalDate.from(modifyDateTime);
        if (isAfterToday(todayDate, modifyDate)) {
            throw new IllegalArgumentException("[ERROR] 수정 일자는 어제 기록까지만 수정할 수 있습니다.");
        }
        int modifyDay = modifyDateTime.getDayOfMonth();
        LocalDateTime previousTime = attendance.get(modifyDay);
        attendance.put(modifyDay, modifyDateTime);
        return previousTime;
    }

    public AttendanceCounter countAttendanceType(final LocalDate todayDate) {
        List<LocalDateTime> history = getAttendanceHistory(todayDate);
        return new AttendanceCounter(history);
    }

    public List<LocalDateTime> getAttendanceHistory(final LocalDate todayDate) {
        int today = todayDate.getDayOfMonth();
        return attendance.entrySet().stream()
                .filter(it -> it.getKey() < today)
                .map(Entry::getValue)
                .toList();
    }

    private boolean isAfterToday(final LocalDate todayDate, final LocalDate modifyDate) {
        return modifyDate.isEqual(todayDate) || modifyDate.isAfter(todayDate);
    }

    public Map<Integer, LocalDateTime> getAttendance() {
        return Collections.unmodifiableMap(attendance);
    }
}
