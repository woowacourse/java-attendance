package domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import exception.AlreadyAttendanceException;
import util.DayUtil;

public class Crew {

    private final String nickname;
    private final Map<LocalDate, LocalTime> attendanceTimes = new HashMap<>();

    public Crew(String name) {
        this.nickname = name;
    }

    public AttendanceStatus attendance(LocalDate date, LocalTime time) {
        validateAlreadyAttendanceDate(date);
        attendanceTimes.put(date, time);
        return getAttendanceStatusByDate(date);
    }

    private void validateAlreadyAttendanceDate(LocalDate date) {
        if (attendanceTimes.containsKey(date)) {
            throw new AlreadyAttendanceException("이미 출석 처리되어 있습니다. 수정 기능을 이용해주세요.");
        }
    }

    public String getName() {
        return nickname;
    }

    public void modifyAttendance(LocalDate date, LocalTime time) {
        attendanceTimes.put(date, time);
    }

    public LocalTime getAttendanceTimeByDate(LocalDate date) {
        return attendanceTimes.get(date);
    }

    public AttendanceStatus getAttendanceStatusByDate(LocalDate date) {
        if(DayUtil.isOffDay(date)) {
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
            if (DayUtil.isOffDay(today.withDayOfMonth(day))) {
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

    public Map<AttendanceStatus, Integer> getAttendanceStatusStatistics(LocalDate today) {
        Map<AttendanceStatus, Integer> statusCounter = new EnumMap<>(AttendanceStatus.class);
        initializeStatusCounter(statusCounter);
        for(int day = 1; day < today.getDayOfMonth(); day++) {
            if (DayUtil.isOffDay(today.withDayOfMonth(day))) {
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
