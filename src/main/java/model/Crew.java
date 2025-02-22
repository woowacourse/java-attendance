package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class Crew {

    private final Map<Integer, LocalDateTime> attendance;

    public Crew(final Map<Integer, LocalDateTime> attendance) {
        this.attendance = new HashMap<>(attendance);
    }

    public void doAttendance(final LocalDateTime attendanceTime) {
        int day = attendanceTime.getDayOfMonth();
        if (attendance.containsKey(day)) {
            throw new IllegalArgumentException("[ERROR] 이미 출석했습니다. 수정 기능을 이용해주세요.");
        }
        attendance.put(day, attendanceTime);
    }

    public LocalDateTime doModify(final LocalDateTime modifyDateTime, final LocalDate todayDate) {
        LocalDate modifyDate = LocalDate.from(modifyDateTime);
        if (modifyDate.isEqual(todayDate) || modifyDate.isAfter(todayDate)) {
            throw new IllegalArgumentException("[ERROR] 수정 일자는 어제 기록까지만 수정할 수 있습니다.");
        }
        int modifyDay = modifyDateTime.getDayOfMonth();
        LocalDateTime previousTime = attendance.get(modifyDay);
        attendance.put(modifyDay, modifyDateTime);
        return previousTime;
    }

    public List<LocalDateTime> getAttendanceHistory(final LocalDate todayDate) {
        int today = todayDate.getDayOfMonth();
        return IntStream.range(1, today)
                .filter(attendance::containsKey)
                .mapToObj(attendance::get)
                .toList();
    }

    public void loadAttendanceHistory(final LocalDateTime attendanceTime) {
        int day = attendanceTime.getDayOfMonth();
        attendance.put(day, attendanceTime);
    }

    public Map<Integer, LocalDateTime> getAttendance() {
        return Collections.unmodifiableMap(attendance);
    }
}
