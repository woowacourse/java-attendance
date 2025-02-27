package domain;

import domain.policy.AttendancePolicy;
import domain.policy.date.AttendanceDatePolicy;
import domain.policy.time.AttendanceTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AttendancesTest {

    private final AttendancePolicy attendancePolicy = new AttendancePolicy(
            new AttendanceDatePolicy(),
            new AttendanceTimePolicy()
    );

    @Test
    @DisplayName("출석을 추가할 수 있다.")
    void canAddAttendance() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.of(LocalDate.of(2024, 12, 12), attendancePolicy);
        AttendanceTime attendanceTime = AttendanceTime.of(LocalTime.of(10, 10), attendancePolicy);
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        // when
        attendances.add(attendance);

        // then
        assertThat(attendances.findByDate(attendanceDate).getAttendanceTime())
                .isEqualTo(attendanceTime);
    }

    @Test
    @DisplayName("출석을 등록했다면, 날짜를 통해서 출석 존재를 알 수 있다.")
    void whenAddAttendanceCanCheckExistByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.of(LocalDate.of(2024, 12, 12), attendancePolicy);
        AttendanceTime attendanceTime = AttendanceTime.of(LocalTime.of(10, 10), attendancePolicy);
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        attendances.add(attendance);

        // when
        // then
        assertThat(attendances.existsByDate(attendanceDate)).isTrue();
    }

    @Test
    @DisplayName("출석을 등록하지 않았다면, 날짜를 통해서 출석 존재를 알 수 없다.")
    void whenNotAddAttendanceCannotCheckExistByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.of(LocalDate.of(2024, 12, 12), attendancePolicy);

        // when
        // then
        assertThat(attendances.existsByDate(attendanceDate)).isFalse();
    }

    @Test
    @DisplayName("출석을 등록했다면, 날짜를 통해서 출석을 찾을 수 있다.")
    void whenAddAttendanceCanCheckFindByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.of(LocalDate.of(2024, 12, 12), attendancePolicy);
        AttendanceTime attendanceTime = AttendanceTime.of(LocalTime.of(10, 10), attendancePolicy);
        Attendance attendance = Attendance.of(attendanceDate, attendanceTime);

        attendances.add(attendance);

        // when
        // then
        assertThat(attendances.findByDate(attendanceDate)).isEqualTo(attendance);
    }

    @Test
    @DisplayName("출석을 등록하지 않았다면, 날짜를 통해서 출석 존재를 알 수 없다.")
    void whenNotAddAttendanceCannotFindByDate() {
        // given
        Attendances attendances = Attendances.initialize();
        AttendanceDate attendanceDate = AttendanceDate.of(LocalDate.of(2024, 12, 12), attendancePolicy);

        // when
        // then
        assertThatThrownBy(() -> attendances.findByDate(attendanceDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("해당 날짜에 출석 기록이 없습니다.");
    }
}