package attendance.view;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class NumberParserTest {

    @Test
    void 숫자가_적힌_문자열을_알려주면_숫자형으로_반환해준다() {
        // Given
        String numberText = "10";
        NumberParser numberParser = new NumberParser();

        // When & Then
        assertThat(numberParser.parse(numberText)).isEqualTo(10);
    }

    @Test
    void 숫자로_변환할_수_없는_문자열은_숫자형으로_만들지_않는다() {
        // Given
        String numberText = "숫자";
        NumberParser numberParser = new NumberParser();

        // When & Then
        assertThatThrownBy(() -> numberParser.parse(numberText))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("올바른 숫자의 문자열을 입력해 주세요");
    }
}
