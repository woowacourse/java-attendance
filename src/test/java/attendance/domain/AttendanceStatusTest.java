package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceStatusTest {

    @CsvSource(value = {
            "17,12,59,OK", "17,13,05,OK", "17,13,06,LATE", "17,13,30,LATE", "17,13,31,ABSENT",
            "18,9,59,OK", "18,10,05,OK", "18,10,06,LATE", "18,10,30,LATE", "18,10,31,ABSENT"
    })
    @ParameterizedTest
    void 출석_날짜와_시간을_알려주면_출석_상태를_알려준다(int day, int hour, int minute, AttendanceStatus expected) {
        AttendanceStatus status = AttendanceStatus.findByAttendanceDateTime(LocalDateTime.of(2025, 2, day, hour, minute));

        assertThat(status).isEqualTo(expected);
    }

}
