package attendance.domain;

import static attendance.domain.AttendanceType.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceResultTest {

    AttendanceTimes attendanceTimes;
    final LocalDate currentDate = LocalDate.of(2024, 12, 7);

    /***
     * 출석 2회, 지각 2회, 결석 1회를 기본값으로 설정한다.
     */
    @BeforeEach
    void init() {
        attendanceTimes = AttendanceTimes.create();
        AttendanceTime attendAttendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 2, 13, 0));
        AttendanceTime attendAttendanceTime2 = AttendanceTime.from(LocalDateTime.of(2024, 12, 3, 10, 0));
        AttendanceTime lateAttendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 4, 10, 8));
        AttendanceTime lateAttendanceTime2 = AttendanceTime.from(LocalDateTime.of(2024, 12, 5, 10, 20));
        AttendanceTime absenceAttendanceTime1 = AttendanceTime.from(LocalDateTime.of(2024, 12, 6, 10, 30));
        attendanceTimes.add(attendAttendanceTime1);
        attendanceTimes.add(attendAttendanceTime2);
        attendanceTimes.add(lateAttendanceTime1);
        attendanceTimes.add(lateAttendanceTime2);
        attendanceTimes.add(absenceAttendanceTime1);
    }

    @DisplayName("출석 기록이 주어졌을 때, 출석유형과 횟수를 판별한다.")
    @Test
    void given_attendance_times_then_calculate_attendance_result() {
        AttendanceResult calculateAttendanceResult = AttendanceResult.calculateAttendanceResult(currentDate,
            attendanceTimes);
        Map<AttendanceType, Long> attendanceResult = calculateAttendanceResult.getAttendanceResult();
        assertThat(attendanceResult.get(ATTENDANCE)).isEqualTo(2);
        assertThat(attendanceResult.get(LATE)).isEqualTo(2);
        assertThat(attendanceResult.get(ABSENCE)).isEqualTo(1);
    }

    @DisplayName("현재 날짜가 2024-12-03일 경우, 주말인 12-01 제외한 12-02만 계산되어야 한다.")
    @Test
    void current_day_2024_12_03_then_calculate_only_12_02() {
        LocalDate currentDate = LocalDate.of(2024, 12, 3);
        AttendanceTimes attendanceTimes = AttendanceTimes.create();
        AttendanceResult attendanceResult = AttendanceResult.calculateAttendanceResult(currentDate,
            attendanceTimes);
        Map<AttendanceType, Long> attendanceResult1 = attendanceResult.getAttendanceResult();
        assertThat(attendanceResult1.get(ATTENDANCE)).isNull();
        assertThat(attendanceResult1.get(LATE)).isNull();
        assertThat(attendanceResult1.get(ABSENCE)).isEqualTo(1);
    }

    @DisplayName("현재 날짜가 2024-12-27일 경우, 주말 7회, 공휴일 1회를 제외한 18회가 계산되어야 한다.")
    @Test
    void current_day_2024_12_27_then_calculate_only_19_times() {
        LocalDate currentDate = LocalDate.of(2024, 12, 27);
        AttendanceTimes attendanceTimes = AttendanceTimes.create();
        AttendanceResult attendanceResult = AttendanceResult.calculateAttendanceResult(currentDate,
            attendanceTimes);
        Map<AttendanceType, Long> attendanceResult1 = attendanceResult.getAttendanceResult();
        assertThat(attendanceResult1.get(ATTENDANCE)).isNull();
        assertThat(attendanceResult1.get(LATE)).isNull();
        assertThat(attendanceResult1.get(ABSENCE)).isEqualTo(18);
    }
}
