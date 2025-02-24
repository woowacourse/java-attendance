package domain.constants;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AnswerCommandTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("사용자의 응답이 'Y'라면 YES를 반환한다.")
        @Test
        public void ofYes() throws Exception {
            // given
            final String userAnswer = "Y";

            // when
            final AnswerCommand actual = AnswerCommand.of(userAnswer);

            // then
            assertThat(actual).isSameAs(AnswerCommand.YES);
        }

        @DisplayName("사용자의 응답이 'N'이라면 NO를 반환한다.")
        @Test
        public void ofNo() throws Exception {
            // given
            final String userAnswer = "N";

            // when
            final AnswerCommand actual = AnswerCommand.of(userAnswer);

            // then
            assertThat(actual).isSameAs(AnswerCommand.NO);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("사용자의 응답이 'Y', 'N' 둘 중 하나가 아니라면, 예외가 발생한다.")
        @Test
        public void of() throws Exception {
            // given
            final String invalidAnswer = "invalidAnswer";

            // when & then
            assertThatThrownBy(() -> AnswerCommand.of(invalidAnswer))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining(ErrorMessage.USER_COMMAND_NOT_FOUND.getMessage());
        }
    }
}
