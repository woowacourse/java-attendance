package domain;

import domain.policy.AttendanceStateRule;
import domain.policy.attend.AttendancePolicy;
import util.TimeMachine;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class Attendances {

    private final Map<AttendanceDate, AttendanceTime> dateToTime;

    private Attendances(Map<AttendanceDate, AttendanceTime> dateToTime) {
        this.dateToTime = dateToTime;
    }

    public static Attendances initialize() {
        return new Attendances(new HashMap<>());
    }

    public Attendance add(Attendance attendance) {
        dateToTime.put(attendance.getAttendanceDate(), attendance.getAttendanceTime());
        return attendance;
    }

    public boolean existsByDate(AttendanceDate attendanceDate) {
        return dateToTime.containsKey(attendanceDate);
    }

    public Attendance findByDate(AttendanceDate attendanceDate) {
        if (existsByDate(attendanceDate)) {
            AttendanceTime attendanceTime = dateToTime.get(attendanceDate);
            return Attendance.of(attendanceDate, attendanceTime);
        }
        throw new IllegalArgumentException("해당 날짜에 출석 기록이 없습니다.");
    }

    public AttendanceCounts calculateAttendanceCounts(Nickname nickname,
                                                      AttendancePolicy attendancePolicy) {
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);

        IntStream.range(1, TimeMachine.dateOfNow().getDayOfMonth())
                .mapToObj(dayOfMonth -> LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, dayOfMonth))
                .filter(attendancePolicy::canAttendDate)
                .map(date -> AttendanceDate.of(date, attendancePolicy))
                .map(attendanceDate -> decisionAttendanceState(attendanceDate, attendancePolicy))
                .forEach(attendanceCounts::increment);

        return attendanceCounts;
    }

    private AttendanceStateRule decisionAttendanceState(AttendanceDate attendanceDate,
                                                        AttendancePolicy attendancePolicy) {
        if (existsByDate(attendanceDate)) {
            return findByDate(attendanceDate).decideAttendanceState(attendancePolicy);
        }
        return AttendanceStateRule.ABSENT;
    }
}
