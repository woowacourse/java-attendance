package attendance.view;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

@DisplayName("메뉴 테스트")
class MenuTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("입력과 일치한 메뉴를 반환한다")
    void shouldReturnMenuMatchingInput(String input, Menu excepted) {
        // when
        Menu result = Menu.find(input);

        // then
        assertThat(result).isEqualTo(excepted);
    }

    @Test
    @DisplayName("잘못된 메뉴 선택의 입력인 경우 예외가 발생한다")
    void shouldThrowExceptionWhenInvalidMenuSelection() {
        // given
        String input = "X";

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Menu.find(input))
                .withMessage("[ERROR] 잘못된 메뉴 선택의 입력입니다.");
    }

    private static Stream<Arguments> shouldReturnMenuMatchingInput() {
        return Stream.of(
                Arguments.of("1", Menu.CHECK),
                Arguments.of("2", Menu.UPDATE),
                Arguments.of("3", Menu.RECORD_SEARCH),
                Arguments.of("4", Menu.RISK_CREW_SEARCH),
                Arguments.of("Q", Menu.QUIT)
        );
    }
}
