package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DateTimeUtil;

public class Crew {

    private final String nickname;
    private final Map<LocalDate, LocalTime> attendanceTimes = new HashMap<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public AttendanceStatus attendance(LocalDate date, LocalTime time) {
        validateDate(date);
        attendanceTimes.put(date, time);
        return getAttendanceStatusByDate(date);
    }

    private void validateDate(LocalDate date) {
        if (attendanceTimes.containsKey(date)) {
            throw new IllegalArgumentException("이미 출석 처리되어 있습니다. 수정 기능을 이용해주세요.");
        }
        if (DateTimeUtil.isOffDay(date)) {
            throw new IllegalArgumentException("주말 및 공휴일에는 출석을 받지 않습니다.");
        }
    }

    public String getNickname() {
        return nickname;
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        attendanceTimes.put(date, time);
    }

    public LocalTime getAttendanceTimeByDate(LocalDate date) {
        return attendanceTimes.get(date);
    }

    public AttendanceStatus getAttendanceStatusByDate(LocalDate date) {
        if(DateTimeUtil.isOffDay(date)) {
            return AttendanceStatus.NONE;
        }
        if(!attendanceTimes.containsKey(date)) {
            return AttendanceStatus.ABSENT;
        }
        return AttendanceStatus.of(date, attendanceTimes.get(date));
    }

    public List<History> getAllHistory(LocalDate today) {
        List<History> histories = new ArrayList<>();
        for (int day = 1; day < today.getDayOfMonth(); day++) {
            if (DateTimeUtil.isOffDay(today.withDayOfMonth(day))) {
                continue;
            }
            LocalDate date = today.withDayOfMonth(day);
            LocalTime time = attendanceTimes.get(date);
            histories.add(
                new History(
                    date,
                    time,
                    getAttendanceStatusByDate(date),
                    time == null
                ));
        }
        return histories;
    }

    public Map<AttendanceStatus, Integer> getAttendanceStatusCounter(LocalDate today) {
        Map<AttendanceStatus, Integer> statusCounter = new EnumMap<>(AttendanceStatus.class);
        initializeStatusCounter(statusCounter);
        for(int day = 1; day < today.getDayOfMonth(); day++) {
            if (DateTimeUtil.isOffDay(today.withDayOfMonth(day))) {
                continue;
            }
            AttendanceStatus attendanceStatus = getAttendanceStatusByDate(LocalDate.of(today.getYear(),
                today.getMonth(), day));
            statusCounter.put(attendanceStatus, statusCounter.getOrDefault(attendanceStatus, 0) + 1);
        }
        return statusCounter;
    }

    private void initializeStatusCounter(Map<AttendanceStatus, Integer> result) {
        Arrays.stream(AttendanceStatus.values()).forEach(status -> result.put(status, 0));
    }
}
