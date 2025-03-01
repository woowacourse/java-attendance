package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class StringParserTest {

    @Test
    void 시간_문자열을_LocalTime으로_파싱한다() {
        // Given
        String input = "10:01";
        LocalTime expected = LocalTime.of(10, 1);

        // When & Then
        assertThat(StringParser.parseLocalTime(input)).isEqualTo(expected);
    }

    @Test
    void 시간_문자열_형식에_맞지_않은_경우_예외가_발생한다() {
        // Given
        String input = "10:1";

        // When & Then
        Assertions.assertThatThrownBy(() -> StringParser.parseLocalTime(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("문자열 형식에 맞지 않습니다.");
    }
}
