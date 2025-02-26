package attendance.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class CrewHistory {

    private final Map<LocalDate, LocalDateTime> attendance;

    public CrewHistory(final Map<LocalDate, LocalDateTime> attendance) {
        this.attendance = new TreeMap<>(attendance);
    }

    public void loadHistory(final LocalDateTime attendanceTime) {
        LocalDate date = LocalDate.from(attendanceTime);
        attendance.put(date, attendanceTime);
    }

    public void attend(final LocalDateTime attendanceTime) {
        LocalDate date = LocalDate.from(attendanceTime);
        if (attendance.containsKey(date)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
        attendance.put(date, attendanceTime);
    }

    public LocalDateTime modify(final LocalDateTime modifyDateTime, final LocalDate todayDate) {
        LocalDate modifyDate = LocalDate.from(modifyDateTime);
        if (isAfterToday(todayDate, modifyDate)) {
            throw new IllegalArgumentException("[ERROR] 수정 일자는 어제 기록까지만 수정할 수 있습니다.");
        }
        LocalDateTime previousTime = attendance.get(modifyDate);
        attendance.put(modifyDate, modifyDateTime);
        return previousTime;
    }

    public AttendanceCounter countAttendanceType(final LocalDate todayDate) {
        List<LocalDateTime> history = getAttendanceHistory(todayDate);
        return new AttendanceCounter(history);
    }

    public List<LocalDateTime> getAttendanceHistory(final LocalDate todayDate) {
        return attendance.entrySet().stream()
                .filter(it -> todayDate.isAfter(it.getKey()))
                .map(Entry::getValue)
                .toList();
    }

    private boolean isAfterToday(final LocalDate todayDate, final LocalDate modifyDate) {
        return modifyDate.isEqual(todayDate) || modifyDate.isAfter(todayDate);
    }

    public Map<LocalDate, LocalDateTime> getAttendance() {
        return Collections.unmodifiableMap(attendance);
    }
}
