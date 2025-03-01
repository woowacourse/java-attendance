package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import attendance.fixture.DateTimeFixture;

class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource({
        "0, ATTENDANCE",
        "5, ATTENDANCE",
        "6, LATENESS",
        "30, LATENESS",
        "31, ABSENCE",
    })
    @DisplayName("등교 시간에 맞는 출석 상태를 반환한다")
    void fromTest(int minute, AttendanceStatus expected) {
        // when
        AttendanceStatus status = AttendanceStatus.from(DateTimeFixture.OTHER_DAY,
            LocalTime.of(10, 0).plusMinutes(minute));

        // then
        assertThat(status).isEqualTo(expected);
    }
}
