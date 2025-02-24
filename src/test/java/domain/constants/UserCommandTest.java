package domain.constants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class UserCommandTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("주어진 문자열을 유저의 커맨드로 올바르게 반환한다.")
        @ParameterizedTest
        @MethodSource("provideUserCommand")
        public void of(final String userCommand, final UserCommand expected) throws Exception {
            // given & when
            final UserCommand actual = UserCommand.of(userCommand);

            // then
            assertThat(actual).isSameAs(expected);
        }

        private static Stream<Arguments> provideUserCommand() {
            return Stream.of(
                    Arguments.of("1", UserCommand.ADD_ATTENDANCE),
                    Arguments.of("2", UserCommand.UPDATE_ATTENDANCE),
                    Arguments.of("3", UserCommand.LOOKUP_CREW_ATTENDANCE),
                    Arguments.of("4", UserCommand.LOOKUP_EXPULSION_CREWS),
                    Arguments.of("Q", UserCommand.QUIT)
            );
        }

    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("존재하지 않는 커맨드를 입력하면 예외가 발생한다.")
        @Test
        public void of() throws Exception {
            // given
            final String invalidUserCommand = "5";

            // when & then
            assertThatThrownBy(() -> UserCommand.of(invalidUserCommand))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.USER_COMMAND_NOT_FOUND.getMessage());
        }

    }

}
