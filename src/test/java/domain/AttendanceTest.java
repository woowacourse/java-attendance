package domain;

import config.AttendancePolicyConfig;
import domain.policy.AttendanceStateRule;
import domain.policy.attend.AttendancePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTest {

    private final AttendancePolicy attendancePolicy = AttendancePolicyConfig.getInstance();

    @Test
    @DisplayName("출석은 자신의 출석 상태를 결정할 수 있다.")
    void attendanceCanDecideSelfState() {
        // given
        Attendance attendance_ATTEND = Attendance.of(
                AttendanceDate.from(LocalDate.of(2024, 12, 13)),
                AttendanceTime.from(LocalTime.of(10, 0))
        );

        Attendance attendance_LATE = Attendance.of(
                AttendanceDate.from(LocalDate.of(2024, 12, 13)),
                AttendanceTime.from(LocalTime.of(10, 6))
        );

        Attendance attendance_ABSENT = Attendance.of(
                AttendanceDate.from(LocalDate.of(2024, 12, 13)),
                AttendanceTime.from(LocalTime.of(10, 31))
        );

        // when
        // then
        assertAll(
                () -> assertThat(attendance_ATTEND.decideAttendanceState(attendancePolicy))
                        .isEqualTo(AttendanceStateRule.ATTEND),
                () -> assertThat(attendance_LATE.decideAttendanceState(attendancePolicy))
                        .isEqualTo(AttendanceStateRule.LATE),
                () -> assertThat(attendance_ABSENT.decideAttendanceState(attendancePolicy))
                        .isEqualTo(AttendanceStateRule.ABSENT)
        );
    }

    @Test
    @DisplayName("내부 값이 같다면, 같은 출석으로 취급한다.")
    void treatedAsTheSameObjectIfValuesAreTheSame() {
        // given
        AttendanceDate attendanceDate1 = AttendanceDate.from(LocalDate.of(2024, 12, 13));
        AttendanceDate attendanceDate2 = AttendanceDate.from(LocalDate.of(2024, 12, 13));

        AttendanceTime attendanceTime1 = AttendanceTime.from(LocalTime.of(10, 10));
        AttendanceTime attendanceTime2 = AttendanceTime.from(LocalTime.of(10, 10));

        Attendance attendance1 = Attendance.of(attendanceDate1, attendanceTime1);
        Attendance attendance2 = Attendance.of(attendanceDate2, attendanceTime2);

        // when
        // then
        assertThat(attendance1).isEqualTo(attendance2);
    }
}
