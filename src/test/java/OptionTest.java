import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.Option;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class OptionTest {

    @DisplayName("유효한 입력을 받으면 해당하는 Option을 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "1, ATTEND",
            "2, EDIT",
            "3, CHECK_RECORDS",
            "4, DISPLAY_EXPULSION_RISK_CREW",
            "Q, QUIT",
            "q, QUIT"
    })
    void should_ReturnCorrectSelectOption_When_ValidInputIsGiven(String inputNumber, Option expectedOption) {
        Option option = Option.from(inputNumber);

        assertThat(option).isEqualTo(expectedOption);
    }

    @DisplayName("유효하지 않은 입력을 받으면 예외를 발생시킨다.")
    @Test
    void should_ThrowException_When_InvalidInputIsGiven() {
        String inputNumber = "99";

        assertThatThrownBy(() -> Option.from(inputNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContainingAll("[ERROR] 유효하지 않은 입력입니다.");
    }
}
