package constant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CommandTest {

    @ParameterizedTest
    @CsvSource({
            "1, ONE",
            "2, TWO",
            "3, THREE",
            "4, FOUR",
            "Q, QUIT",
    })
    void 명령_문자열을_Command_객체로_변환한다(String rawCommand, Command expected) {
        //when
        Command actual = Command.find(rawCommand);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 잘못된_명령_문자열일_경우_예외를_발생시킨다() {
        //when & then
        assertThatThrownBy(() -> Command.find("q"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 옵션입니다.");
    }
}