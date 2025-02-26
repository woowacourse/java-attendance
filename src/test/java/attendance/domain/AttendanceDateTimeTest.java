package attendance.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Year;

import static org.assertj.core.api.Assertions.*;

public class AttendanceDateTimeTest {

    @Test
    void 출석_일자와_시간을_알려주면_해당_시간대의_출석시간_객체를_생성한다() {
        // Given
        LocalDateTime attendanceDateTime = Year.of(2025).atMonth(2).atDay(26).atTime(10, 00);

        // When & Then
        assertThatCode(() -> new AttendanceDateTime(attendanceDateTime))
                .doesNotThrowAnyException();
    }
}
