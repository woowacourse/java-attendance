package attendance.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    private static final LocalDate DATE = LocalDate.of(2025, 02, 25);
    private static final LocalTime TIME = LocalTime.of(10, 00);

    @Test
    @DisplayName("등교 시간에 맞는 출석 상태를 반환한다")
    void fromTest() {
        // when then
        assertSoftly(softly -> {
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(0))).isEqualTo(AttendanceStatus.ATTENDANCE);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(5))).isEqualTo(AttendanceStatus.ATTENDANCE);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(6))).isEqualTo(AttendanceStatus.LATENESS);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(30))).isEqualTo(AttendanceStatus.LATENESS);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(31))).isEqualTo(AttendanceStatus.ABSENCE);
        });
    }
}
