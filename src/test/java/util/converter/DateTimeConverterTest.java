package util.converter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import exception.ErrorException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DateTimeConverterTest {

    @Test
    @DisplayName("문자열 날짜 및 시간 변환 기능 테스트")
    void 문자열_날짜_및_시간_변환_기능_테스트() {
        // given
        String dateTimeStr = "2024-12-20 10:05";
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 20, 10, 5);
        // when
        LocalDateTime convertedDateTime = DateTimeConverter.convertStringToDateTime(dateTimeStr);
        // then
        assertEquals(dateTime, convertedDateTime);
    }

    @Test
    @DisplayName("문자열 날짜 및 시간 변환 예외 테스트")
    void 문자열_날짜_및_시간_변환_예외_테스트() {
        // given
        String dateTimeStr = "2024-13-20 10:05";
        // when & then
        assertThatThrownBy(() -> DateTimeConverter.convertStringToDateTime(dateTimeStr))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }
}
