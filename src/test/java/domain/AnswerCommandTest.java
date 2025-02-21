package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AnswerCommandTest {


    @Nested
    @DisplayName("성공 테스트")
    class SuccessTest {
        @DisplayName("사용자가 입력한 값으로 커멘드를 찾는다.")
        @Test
        void findByCommandTest() {
            //given
            final String input = "Y";

            //when
            final AnswerCommand command = AnswerCommand.findByCommand(input);

            //then
            assertThat(command).isEqualTo(AnswerCommand.YES);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailTest {
        @Test
        @DisplayName("사용자가 올바르지 않은 커멘드를 입력하여 예외가 발생한다.")
        void findByCommandTest() {
            //given
        	final String input = "C";

            //when
            //then
            assertThatIllegalArgumentException().isThrownBy(() -> AnswerCommand.findByCommand(input));

        }
    }

}
