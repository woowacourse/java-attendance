package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeTest {

    @DisplayName("캠퍼스 운영 시간 외의 시간이 들어올 경우 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,25,7,59", "2025,2,25,23,1"
    })
    void 캠퍼스_운영_시간_외의_시간이_들어올_경우_예외를_발생한다(final int year, final int month, final int dayOfMonth, final int hour,
                                          final int minute) {

        // given

        // when & then
        assertThatThrownBy(() -> new Time(LocalDateTime.of(year, month, dayOfMonth, hour, minute)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시간에 들어올 경우 예외를 발생하지 않는다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2025,2,25,8,0", "2025,2,25,23,0"
    })
    void 캠퍼스_운영_시간에_들어올_경우_예외를_발생하지_않는다(final int year, final int month, final int dayOfMonth, final int hour,
                                        final int minute) {

        // given

        // when & then
        assertThatCode(() -> {
            Time time = new Time(LocalDateTime.of(2025, 2, 25, 8, 0));
        }).doesNotThrowAnyException();
    }

}
