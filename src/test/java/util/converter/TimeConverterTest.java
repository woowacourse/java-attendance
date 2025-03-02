package util.converter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import exception.ErrorException;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TimeConverterTest {

    @Test
    @DisplayName("문자열 시간 변환 기능 테스트")
    void 문자열_시간_변환_기능_테스트() {
        // given
        String timeStr = "10:05";
        LocalTime time = LocalTime.of(10, 5);
        // when
        LocalTime convertedTime = TimeConverter.convertStringToTime(timeStr);
        // then
        assertEquals(time, convertedTime);
    }

    @Test
    @DisplayName("문자열 시간 변환 예외 테스트")
    void 문자열_시간_변환_예외_테스트() {
        // given
        String timeStr = "25:05";
        // when & then
        assertThatThrownBy(() -> TimeConverter.convertStringToTime(timeStr))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }
}
