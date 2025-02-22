package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @ParameterizedTest
    @CsvSource({
        "2025-02-17T13:05:00,CHECKIN",
        "2025-02-17T13:06:00,LATE",
        "2025-02-17T13:31:00,ABSENCE",
        "2025-02-18T10:05:00,CHECKIN",
        "2025-02-19T10:06:00,LATE",
        "2025-02-20T10:31:00,ABSENCE",
    })
    void 날짜와_시간에_따라_출석상태를_결정한다(LocalDateTime attendAt, AttendanceStatus expected) {
        AttendanceStatus status = AttendanceStatus.compute(attendAt);
        assertThat(status).isEqualTo(expected);
    }

}
