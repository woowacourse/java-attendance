package domain;

import config.AttendancePolicyConfig;
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
        validateExistForAdd(attendance.getAttendanceDate());
        dateToTime.put(attendance.getAttendanceDate(), attendance.getAttendanceTime());
        return attendance;
    }

    public Attendance update(Attendance attendance) {
        validateExistForUpdate(attendance.getAttendanceDate());
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

    public AttendanceCounts calculateAttendanceCounts(Nickname nickname) {
        AttendancePolicy attendancePolicy = AttendancePolicyConfig.getInstance();
        AttendanceCounts attendanceCounts = AttendanceCounts.initialize(nickname);

        IntStream.range(1, TimeMachine.dateOfNow().getDayOfMonth())
                .mapToObj(dayOfMonth -> LocalDate.of(TimeMachine.FIXED_YEAR, TimeMachine.FIXED_MONTH, dayOfMonth))
                .filter(attendancePolicy::canAttendDate)
                .map(AttendanceDate::from)
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

    private void validateExistForAdd(AttendanceDate attendanceDate) {
        if (existsByDate(attendanceDate)) {
            throw new IllegalArgumentException("이미 출석한 경우, 수정 기능을 이용해주세요.");
        }
    }

    private void validateExistForUpdate(AttendanceDate attendanceDate) {
        if (!existsByDate(attendanceDate)) {
            throw new IllegalArgumentException("출석하지 않은 경우, 수정 기능을 이용할 수 없습니다.");
        }
    }
}
