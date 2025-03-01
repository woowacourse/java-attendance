package attendance.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ParserTest {

    @Test
    void 문자열을_정수형으로_변환한다() {

        // given

        // when
        final int result = Parser.parseInt("3");

        // then
        Assertions.assertThat(result).isEqualTo(3);
    }

    @Test
    void 숫자가_아닌_문자열을_변환할_경우_예외가_발생한다() {

        // given

        // when & then
        Assertions.assertThatThrownBy(() -> Parser.parseInt("not_int"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 올바른 숫자를 입력해 주세요.");
    }
}
