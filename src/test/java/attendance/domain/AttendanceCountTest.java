package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCountTest {
    @DisplayName("기능: 해당 AttendanceCount 출석 횟수 증가 및 초기화")
    @Test
    void updateAttendanceCount() {
        AttendanceCount attendanceCount = new AttendanceCount();

        attendanceCount.incrementSafeCount();
        attendanceCount.incrementLateCount();
        attendanceCount.incrementAbsentCount();

        assertThat(attendanceCount.getSafeCount()).isEqualTo(1);
        assertThat(attendanceCount.getLateCount()).isEqualTo(1);
        assertThat(attendanceCount.getAbsentCount()).isEqualTo(1);

        attendanceCount.resetAttendanceCount();

        assertThat(attendanceCount.getSafeCount()).isZero();
        assertThat(attendanceCount.getLateCount()).isZero();
        assertThat(attendanceCount.getAbsentCount()).isZero();
    }
}
