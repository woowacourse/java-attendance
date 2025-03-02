package util.converter;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

import exception.ErrorException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class DayConverterTest {

    @Test
    @DisplayName("문자열 숫자 변환 기능 테스트")
    void 문자열_숫자_변환_기능_테스트() {
        // given
        String day = "2";
        // when
        int convertedDay = DayConverter.convertDayToNumber(day);
        // then
        assertEquals(convertedDay, Integer.parseInt(day));
    }

    @Test
    @DisplayName("문자열 숫자 변환 자료형_예외 테스트")
    void 문자열_숫자_변환_자료형_예외_테스트() {
        // given
        String day = "a";
        // when & then
        assertThatThrownBy(() ->  DayConverter.convertDayToNumber(day))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @ParameterizedTest
    @CsvSource({"0", "32"})
    @DisplayName("문자열 숫자 변환 자료형_예외 테스트")
    void 문자열_숫자_변환_범위_예외_테스트(String day) {;
        // given & when & then
        assertThatThrownBy(() ->  DayConverter.convertDayToNumber(day))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }
}
