package domain;

import domain.policy.attend.AttendancePolicy;
import domain.policy.attend.date.AttendanceDatePolicy;
import domain.policy.attend.time.AttendanceTimePolicy;
import domain.policy.AttendanceStateRule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class AttendanceTest {

    private final AttendancePolicy attendancePolicy = new AttendancePolicy(
            new AttendanceDatePolicy(),
            new AttendanceTimePolicy()
    );

    @Test
    @DisplayName("출석은 자신의 출석 상태를 결정할 수 있다.")
    void attendanceCanDecideSelfState() {
        // given
        Attendance attendance_ATTEND = Attendance.of(
                AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy),
                AttendanceTime.of(LocalTime.of(10, 0), attendancePolicy)
        );

        Attendance attendance_LATE = Attendance.of(
                AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy),
                AttendanceTime.of(LocalTime.of(10, 6), attendancePolicy)
        );

        Attendance attendance_ABSENT = Attendance.of(
                AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy),
                AttendanceTime.of(LocalTime.of(10, 31), attendancePolicy)
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
        AttendanceDate attendanceDate1 = AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy);
        AttendanceDate attendanceDate2 = AttendanceDate.of(LocalDate.of(2024, 12, 13), attendancePolicy);

        AttendanceTime attendanceTime1 = AttendanceTime.of(LocalTime.of(10, 10), attendancePolicy);
        AttendanceTime attendanceTime2 = AttendanceTime.of(LocalTime.of(10, 10), attendancePolicy);

        Attendance attendance1 = Attendance.of(attendanceDate1, attendanceTime1);
        Attendance attendance2 = Attendance.of(attendanceDate2, attendanceTime2);

        // when
        // then
        assertThat(attendance1).isEqualTo(attendance2);
    }
}
