package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTest {

    private final Crew crew = new Crew("빙봉");

    @Test
    void 크루와_출석_시간을_알려주면_출석이_생성된다() {
        assertDoesNotThrow(() -> new Attendance(crew, LocalDateTime.of(2025, 2, 18, 10, 0)));
    }

    @Test
    void 휴일은_출석할_수_없다() {
        assertThatThrownBy(() -> new Attendance(crew, LocalDateTime.of(2025, 2, 16, 10, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2월 16일 일요일은 등교일이 아닙니다.");
    }

    @CsvSource(value = {"7,59", "23,1"})
    @ParameterizedTest
    void 캠퍼스_운영_시간이_아니면_출석할_수_없다(int hour, int minute) {
        assertThatThrownBy(() -> new Attendance(crew, LocalDateTime.of(2025, 2, 18, hour, minute)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%02d:%02d은 캠퍼스 운영 시간이 아닙니다.", hour, minute);
    }

}
