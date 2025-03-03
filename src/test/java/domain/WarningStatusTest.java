package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WarningStatusTest {
    @Test
    @DisplayName("출석 상태 개수로부터 정상 상태를 계산한다.")
    void should_return_CLEAR_warning_status_by_attendance_status_count() {
        // given
        AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount(
                Map.of(AttendanceStatus.ATTENDANT, 0L, AttendanceStatus.LATE, 0L, AttendanceStatus.ABSENT, 0L));

        // when
        WarningStatus warningStatus = WarningStatus.calculateWarningStatus(attendanceStatusCount);

        // then
        assertThat(warningStatus).isEqualTo(WarningStatus.CLEAR);
    }

    @Test
    @DisplayName("출석 상태 개수로부터 경고 상태를 계산한다.")
    void should_return_WARNING_warning_status_by_attendance_status_count() {
        // given
        AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount(
                Map.of(AttendanceStatus.ATTENDANT, 0L, AttendanceStatus.LATE, 0L, AttendanceStatus.ABSENT, 2L));

        // when
        WarningStatus warningStatus = WarningStatus.calculateWarningStatus(attendanceStatusCount);

        // then
        assertThat(warningStatus).isEqualTo(WarningStatus.WARNING);
    }

    @Test
    @DisplayName("출석 상태 개수로부터 면담 상태를 계산한다.")
    void should_return_INTERVIEW_warning_status_by_attendance_status_count() {
        // given
        AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount(
                Map.of(AttendanceStatus.ATTENDANT, 0L, AttendanceStatus.LATE, 0L, AttendanceStatus.ABSENT, 3L));

        // when
        WarningStatus warningStatus = WarningStatus.calculateWarningStatus(attendanceStatusCount);

        // then
        assertThat(warningStatus).isEqualTo(WarningStatus.INTERVIEW);
    }

    @Test
    @DisplayName("출석 상태 개수로부터 제적 상태를 계산한다.")
    void should_return_EXPEL_warning_status_by_attendance_status_count() {
        // given
        AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount(
                Map.of(AttendanceStatus.ATTENDANT, 0L, AttendanceStatus.LATE, 0L, AttendanceStatus.ABSENT, 6L));

        // when
        WarningStatus warningStatus = WarningStatus.calculateWarningStatus(attendanceStatusCount);

        // then
        assertThat(warningStatus).isEqualTo(WarningStatus.EXPEL);
    }

    @Test
    @DisplayName("출석 상태 개수로부터 제적 상태를 계산한다.")
    void should_return_EXPEL_warning_status_by_attendance_status_count2() {
        // given
        AttendanceStatusCount attendanceStatusCount = new AttendanceStatusCount(
                Map.of(AttendanceStatus.ATTENDANT, 0L, AttendanceStatus.LATE, 3L, AttendanceStatus.ABSENT, 5L));

        // when
        WarningStatus warningStatus = WarningStatus.calculateWarningStatus(attendanceStatusCount);

        // then
        assertThat(warningStatus).isEqualTo(WarningStatus.EXPEL);
    }
}
