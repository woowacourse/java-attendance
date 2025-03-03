package attendance.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ParserTest {

    @DisplayName("올바르게 숫자로 파싱한다.")
    @Test
    void 올바르게_숫자로_파싱한다() {

        // given
        String input = "123";

        // when
        int parsingInput = Parser.parseToInt(input);

        // then
        assertThat(parsingInput).isEqualTo(Integer.parseInt(input));
    }

    @DisplayName("올바르지 않은 값이면 예외가 발생한다.")
    @Test
    void 올바르지_않은_값이면_예외가_발생한다() {

        // given
        String input = "123A";

        // when & then
        assertThatThrownBy(() -> Parser.parseToInt(input))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바르지 않은 입력입니다.");
    }
}
