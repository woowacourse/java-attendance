package domain;

import domain.rule.AttendanceDateRule;
import domain.rule.AttendanceStateRule;
import util.TimeMachine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Attendances {

    private final Map<AttendanceDate, AttendanceTime> attendances;

    private Attendances(Map<AttendanceDate, AttendanceTime> attendances) {
        this.attendances = attendances;
    }

    public static Attendances create() {
        return new Attendances(new HashMap<>());
    }

    public Attendance add(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        validateExist(attendanceDate);
        attendances.put(attendanceDate, attendanceTime);
        return Attendance.from(attendanceDate, attendanceTime);
    }

    public Attendance update(AttendanceDate attendanceDate, AttendanceTime attendanceTime) {
        attendances.put(attendanceDate, attendanceTime);
        return Attendance.from(attendanceDate, attendanceTime);
    }

    public AttendanceTime findByDate(AttendanceDate attendanceDate) {
        return attendances.get(attendanceDate);
    }

    public boolean existsByDate(AttendanceDate attendanceDate) {
        return attendances.containsKey(attendanceDate);
    }

    public AttendanceStatistics calculateStatistics(String nickname, LocalDate today) {
        Map<AttendanceStateRule, Integer> statisticsFormat = IntStream.range(1, today.getDayOfMonth())
                .mapToObj(dayOfMonth -> LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, dayOfMonth))
                .filter(AttendanceDateRule::canAttendDay)
                .map(AttendanceDate::from)
                .map(this::decisionAttendanceState)
                .collect(Collectors.toMap(state -> state, state -> 1,
                        Integer::sum,
                        AttendanceStatistics::getFormat));

        return AttendanceStatistics.from(nickname, statisticsFormat);
    }

    private AttendanceStateRule decisionAttendanceState(AttendanceDate attendanceDate) {
        if (existsByDate(attendanceDate)) {
            return findByDate(attendanceDate).checkAttendanceState(attendanceDate.isSpecialDay());
        }
        return AttendanceStateRule.ABSENT;
    }

    private void validateExist(AttendanceDate attendanceDate) {
        if (existsByDate(attendanceDate)) {
            throw new IllegalArgumentException("이미 출석한 경우, 수정 기능을 이용해주세요.");
        }
    }
}
