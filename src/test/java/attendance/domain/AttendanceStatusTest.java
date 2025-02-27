package attendance.domain;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    private static final LocalDate DATE = LocalDate.of(2025, 02, 25);
    private static final LocalTime TIME = LocalTime.of(10, 00);

    @ParameterizedTest
    @CsvSource({
        "0, ATTENDANCE",
        "5, ATTENDANCE",
        "6, LATENESS",
        "30, LATENESS",
        "31, ABSENCE",
    })
    @DisplayName("등교 시간에 맞는 출석 상태를 반환한다")
    void fromTest(int minute, String statusName) {
        // when then
        assertSoftly(softly -> {
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(minute)).name()).isEqualTo(statusName);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(minute)).name()).isEqualTo(statusName);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(minute)).name()).isEqualTo(statusName);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(minute)).name()).isEqualTo(statusName);
            softly.assertThat(AttendanceStatus.from(DATE, TIME.plusMinutes(minute)).name()).isEqualTo(statusName);
        });
    }
}
