package attendance.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTimeTest {

    @CsvSource(value = {
            "7,59", "23,01"
    })
    @ParameterizedTest
    void 캠퍼스_운영_시간을_벗어나면_출석이_불가능하다(int hour, int minute) {
        assertThatThrownBy(() -> new AttendanceTime(LocalTime.of(hour, minute)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%02d:%02d은 캠퍼스 운영 시간이 아닙니다.".formatted(hour, minute));
    }

}
