package domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendanceBook {

    private final Map<String, Attends> attendsPerCrew;

    public AttendanceBook() {
        this.attendsPerCrew = new HashMap<>();
    }

    public void registerName(String name) {
        attendsPerCrew.putIfAbsent(name, new Attends(new ArrayList<>()));
    }

    public void attend(String name, Attend attend) {
        validateIsNameExist(name);
        OperationTime.validateAttendableDay(attend);
        OperationTime.validateAttendableTime(attend);
        Attends attends = attendsPerCrew.get(name);
        attends.addAttend(attend);
    }

    public Attends findByName(String name) {
        validateIsNameExist(name);
        return attendsPerCrew.get(name);
    }

    public void edit(String name, Attend attend) {
        validateIsNameExist(name);
        OperationTime.validateAttendableDay(attend);
        OperationTime.validateAttendableTime(attend);
        Attends attends = attendsPerCrew.get(name);
        attends.edit(attend);
    }

    public List<Attend> getAttends(String name) {
        validateIsNameExist(name);
        List<Integer> days = Current.TODAY.getAttendUntilDay();
        return attendsPerCrew.get(name)
                .getAttends(days);
    }

    private void validateIsNameExist(String name) {
        if (!attendsPerCrew.containsKey(name)) {
            throw new IllegalArgumentException("출석부에 존재하지 않는 크루입니다.");
        }
    }

    public Attend findByNameAndDay(String name, int day) {
        Attends attends = attendsPerCrew.get(name);
        return attends.findByDay(day);
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

    public AttendanceResults checkAttendance(String name, List<Integer> days) {
        Attends attends = findByName(name);
        List<AttendanceResult> result = days.stream()
                .map(day -> getAttendanceResult(attends, day))
                .toList();
        return new AttendanceResults(result);
    }

    private void addWarningCrew(List<WarningCrew> result, WarningCrew warningCrew, WarningStatus warningStatus) {
        if (warningStatus != WarningStatus.CLEAR) {
            result.add(warningCrew);
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

    public AttendStatus checkAttendance(Attend attend) {
        return AttendStatus.findAttendStatus(attend);
    }
}
