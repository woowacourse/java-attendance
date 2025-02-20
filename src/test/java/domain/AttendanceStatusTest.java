package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    @DisplayName("출석 시간에 따라 올바른 출석 상태를 반환한다.")
    void ofTest() {
        assertThat(AttendanceStatus.of(LocalDate.of(2025, 02, 03), LocalTime.of(13, 05))).isEqualTo(
            AttendanceStatus.ATTENDANCE);
        assertThat(AttendanceStatus.of(LocalDate.of(2025, 02, 03), LocalTime.of(13, 06))).isEqualTo(
            AttendanceStatus.LATE);
        assertThat(AttendanceStatus.of(LocalDate.of(2025, 02, 03), LocalTime.of(13, 31))).isEqualTo(
            AttendanceStatus.ABSENT);
    }
}
