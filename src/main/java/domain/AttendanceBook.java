package domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import util.DateUtil;

public class AttendanceBook {
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final Map<String, Attends> attendanceBook;

    public AttendanceBook() {
        this.attendanceBook = new HashMap<>();
    }

    public void registerName(String name) {
        if (attendanceBook.containsKey(name)) {
            return;
        }
        attendanceBook.put(name, new Attends(new ArrayList<>()));
    }

    public void attend(String name, Attend attend) {
        validateIsNameExist(name);
        validateAttendableDay(attend);
        validateAttendableTime(attend);
        Attends attends = attendanceBook.get(name);
        attends.addAttend(attend);
    }

    public void edit(String name, Attend attend) {
        validateIsNameExist(name);
        validateAttendableDay(attend);
        validateAttendableTime(attend);
        Attends attends = attendanceBook.get(name);
        attends.edit(attend);
    }

    private void validateAttendableTime(Attend attend) {
        if (DateUtil.isTimeOff(attend, START_TIME, END_TIME)) {
            throw new IllegalArgumentException("운영 시간 외에는 출석할 수 없음");
        }
    }

    private void validateAttendableDay(Attend attend) {
        if (DateUtil.isDayOff(attend)) {
            throw new IllegalArgumentException("쉬는날은 출석할 수 없음");
        }
    }

    public Attends findByName(String name) {
        validateIsNameExist(name);
        return attendanceBook.get(name);
    }

    public Attend findByNameAndDay(String name, int day) {
        Attends attends = attendanceBook.get(name);
        return attends.findByDay(day);
    }


    public List<Attend> getAttends(String name) {
        validateIsNameExist(name);
        List<Integer> days = DateUtil.getAttendUntilDay(Current.TODAY.getYesterday());
        return attendanceBook.get(name)
                .getAttends(days);
    }

    private void validateIsNameExist(String name) {
        if (!attendanceBook.containsKey(name)) {
            throw new IllegalArgumentException("출석부에 존재하지 않는 크루입니다.");
        }
    }

    public AttendStatus checkAttendance(Attend attend) {
        return AttendStatus.calculateAttend(attend);
    }

    public AttendanceResults checkAttendance(String name, List<Integer> days) {
        List<AttendanceResult> result = new ArrayList<>();
        Attends attends = findByName(name);
        for (int day : days) {
            result.add(getAttendanceResult(attends, day));
        }
        return new AttendanceResults(result);
    }

    private AttendanceResult getAttendanceResult(Attends attends, int day) {
        if (attends.hasDayEqualsAttend(day)) {
            Attend attend = attends.findByDay(day);
            return new AttendanceResult(attend, AttendStatus.calculateAttend(attend));
        }
        Attend attend = Attend.fromDay(day);
        return new AttendanceResult(attend, AttendStatus.ABSENCE);
    }

    public List<WarningCrew> checkWarningCrews(List<Integer> days) {
        return attendanceBook.keySet().stream()
                .filter(name -> isWarningCrew(name, days))
                .map(name -> new WarningCrew(name, checkAttendance(name, days).countAttendStatus()))
                .collect(Collectors.toList());
    }

    private boolean isWarningCrew(String name, List<Integer> days) {
        WarningStatus warningStatus = judgeWarningStatus(name, days);
        return warningStatus != WarningStatus.CLEAR;
    }

    private WarningStatus judgeWarningStatus(String name, List<Integer> days) {
        AttendCount attendCount = checkAttendance(name, days).countAttendStatus();
        return WarningStatus.judgeWarningStatus(attendCount);
    }
}
