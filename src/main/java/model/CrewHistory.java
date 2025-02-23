package model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class CrewHistory {

    private final String nickname;
    private final Map<Integer, LocalDateTime> attendance;

    public CrewHistory(final String nickname, final Map<Integer, LocalDateTime> attendance) {
        this.nickname = nickname;
        this.attendance = new HashMap<>(attendance);
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

    public Map<AttendanceType, Integer> countAttendanceType(final LocalDate todayDate) {
        List<LocalDateTime> history = getAttendanceHistory(todayDate);
        return countAttendanceType(history);
    }

    public Map<AttendanceType, Integer> countAttendanceType(final List<LocalDateTime> history) {
        Map<AttendanceType, Integer> result = initialize();
        for (LocalDateTime attendanceTime : history) {
            result.merge(AttendanceType.from(attendanceTime), 1, Integer::sum);
        }
        return Collections.unmodifiableMap(result);
    }

    public List<LocalDateTime> getAttendanceHistory(final LocalDate todayDate) {
        int today = todayDate.getDayOfMonth();
        return IntStream.range(1, today)
                .filter(attendance::containsKey)
                .mapToObj(attendance::get)
                .toList();
    }

    private Map<AttendanceType, Integer> initialize() {
        Map<AttendanceType, Integer> result = new EnumMap<>(AttendanceType.class);
        result.put(AttendanceType.출석, 0);
        result.put(AttendanceType.지각, 0);
        result.put(AttendanceType.결석, 0);
        return result;
    }

    private boolean isAfterToday(final LocalDate todayDate, final LocalDate modifyDate) {
        return modifyDate.isEqual(todayDate) || modifyDate.isAfter(todayDate);
    }

    public String getNickname() {
        return nickname;
    }

    public Map<Integer, LocalDateTime> getAttendance() {
        return Collections.unmodifiableMap(attendance);
    }
}
