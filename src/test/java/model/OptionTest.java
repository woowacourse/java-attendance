package model;

import static constant.ErrorMessage.INVALID_OPTION_FORMAT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class OptionTest {

    @Test
    @DisplayName("옵션을 Option Enum으로 반환한다.")
    void test1() {
        // given
        String one = "1";
        String two = "2";
        String three = "3";
        String four = "4";
        String quitUpper = "Q";
        String quitLower = "q";

        // when
        Option oneOption = Option.find(one);
        Option twoOption = Option.find(two);
        Option threeOption = Option.find(three);
        Option fourOption = Option.find(four);
        Option quitUpperOption = Option.find(quitUpper);
        Option quitLowerOption = Option.find(quitLower);

        // then
        assertThat(oneOption).isEqualTo(Option.ONE);
        assertThat(twoOption).isEqualTo(Option.TWO);
        assertThat(threeOption).isEqualTo(Option.THREE);
        assertThat(fourOption).isEqualTo(Option.FOUR);
        assertThat(quitUpperOption).isEqualTo(Option.QUIT);
        assertThat(quitLowerOption).isEqualTo(Option.QUIT);
    }

    @Test
    @DisplayName("유효하지 않은 옵션 입력 시 예외가 발생한다.")
    void test2() {
        // given
        String zero = "0";

        // when & then
        assertThatThrownBy(() -> Option.find(zero))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(INVALID_OPTION_FORMAT.getMessage());
    }
}
