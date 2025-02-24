package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCountTest {
    @DisplayName("기능: 출석 횟수 초기화 테스트")
    @Test
    void resetSafeCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementSafeCount();
        attendanceCount.resetAttendanceCount();

        assertThat(attendanceCount.getSafeCount()).isZero();
    }

    @DisplayName("기능: 지각 횟수 초기화 테스트")
    @Test
    void resetLateCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementLateCount();
        attendanceCount.resetAttendanceCount();

        assertThat(attendanceCount.getLateCount()).isZero();
    }

    @DisplayName("기능: 결석 횟수 초기화 테스트")
    @Test
    void resetAbsentCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementAbsentCount();
        attendanceCount.resetAttendanceCount();

        assertThat(attendanceCount.getAbsentCount()).isZero();
    }

    @DisplayName("기능: 출석 횟수 증가 테스트")
    @Test
    void incrementSafeCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementSafeCount();

        assertThat(attendanceCount.getSafeCount()).isEqualTo(1);
    }

    @DisplayName("기능: 지각 횟수 증가 테스트")
    @Test
    void incrementLateCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementLateCount();

        assertThat(attendanceCount.getLateCount()).isEqualTo(1);
    }

    @DisplayName("기능: 결석 횟수 증가 테스트")
    @Test
    void incrementAbsentCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementAbsentCount();

        assertThat(attendanceCount.getAbsentCount()).isEqualTo(1);
    }
}
