package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeTest {

    @DisplayName("캠퍼스 운영 시간 외의 시간이 들어올 경우 예외를 발생한다.")
    @Test
    void 캠퍼스_운영_시간_외의_시간이_들어올_경우_예외를_발생한다() {

        // given

        // when & then
        assertThatThrownBy(() -> new Time(LocalDateTime.of(2025, 2, 25, 7, 30)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간이 아닙니다.");
    }

    @DisplayName("캠퍼스 운영 시간에 들어올 경우 예외를 발생하지 않는다.")
    @Test
    void 캠퍼스_운영_시간에_들어올_경우_예외를_발생하지_않는다() {

        // given

        // when & then
        assertThatCode(() -> {
            Time time = new Time(LocalDateTime.of(2025, 2, 25, 8, 0));
        }).doesNotThrowAnyException();
    }

}
