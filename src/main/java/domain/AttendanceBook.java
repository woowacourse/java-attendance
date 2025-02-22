package domain;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {
    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime LATE_TIME = LocalTime.of(10, 5);
    private static final LocalTime ABSENCE_TIME = LocalTime.of(10, 30);
    private static final LocalTime END_TIME = LocalTime.of(23, 0);

    private final Map<String, Attends> attendsPerCrew;

    public AttendanceBook() {
        this.attendsPerCrew = new HashMap<>();
    }

    public void registerName(String name) {
        if (attendsPerCrew.containsKey(name)) {
            return;
        }
        attendsPerCrew.put(name, new Attends(new ArrayList<>()));
    }

    public void attend(String name, Attend attend) {
        validateIsNameExist(name);
        validateAttendableDay(attend);
        validateAttendableTime(attend);
        Attends attends = attendsPerCrew.get(name);
        attends.addAttend(attend);
    }

    public void edit(String name, Attend attend) {
        validateIsNameExist(name);
        validateAttendableDay(attend);
        validateAttendableTime(attend);
        Attends attends = attendsPerCrew.get(name);
        attends.edit(attend);
    }

    public Attends findByName(String name) {
        validateIsNameExist(name);
        return attendsPerCrew.get(name);
    }

    public Attend findByNameAndDay(String name, int day) {
        Attends attends = attendsPerCrew.get(name);
        return attends.findByDay(day);
    }


    public List<Attend> getAttends(String name) {
        validateIsNameExist(name);
        List<Integer> days = Current.TODAY.getAttendUntilDay();
        return attendsPerCrew.get(name)
                .getAttends(days);
    }

    public AttendStatus checkAttendance(Attend attend) {
        return Arrays.stream(AttendStatus.values())
                .filter(attendStatus -> attendStatus.match(attend, LATE_TIME, ABSENCE_TIME))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public AttendanceResults checkAttendance(String name, List<Integer> days) {
        List<AttendanceResult> result = new ArrayList<>();
        Attends attends = findByName(name);
        for (int day : days) {
            result.add(getAttendanceResult(attends, day));
        }
        return new AttendanceResults(result);
    }

    public List<WarningCrew> checkWarningCrews(List<Integer> days) {
        List<WarningCrew> result = new ArrayList<>();
        for (final String name : attendsPerCrew.keySet()) {
            AttendCount attendCount = checkAttendance(name, days).countAttendStatus();
            WarningStatus warningStatus = attendCount.judgeWarning();
            WarningCrew warningCrew = new WarningCrew(name, attendCount);
            addWarningCrew(result, warningCrew, warningStatus);
        }
        return result;
    }

    private void validateAttendableTime(Attend attend) {
        if (attend.isTimeOff(START_TIME, END_TIME)) {
            throw new IllegalArgumentException("운영 시간 외에는 출석할 수 없음");
        }
    }

    private void validateAttendableDay(Attend attend) {
        if (attend.isDayOff()) {
            throw new IllegalArgumentException("쉬는날은 출석할 수 없음");
        }
    }

    private AttendanceResult getAttendanceResult(Attends attends, int day) {
        if (attends.hasDayEqualsAttend(day)) {
            Attend attend = attends.findByDay(day);
            return new AttendanceResult(attend, checkAttendance(attend));
        }
        Attend attend = Attend.fromDay(day);
        return new AttendanceResult(attend, AttendStatus.ABSENCE);
    }

    private void validateIsNameExist(String name) {
        if (!attendsPerCrew.containsKey(name)) {
            throw new IllegalArgumentException("출석부에 존재하지 않는 크루입니다.");
        }
    }

    private void addWarningCrew(List<WarningCrew> result, WarningCrew warningCrew, WarningStatus warningStatus) {
        if (warningStatus != WarningStatus.CLEAR) {
            result.add(warningCrew);
        }
    }
}
