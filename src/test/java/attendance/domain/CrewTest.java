package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrewTest {
    @Nested
    class equals {
        @DisplayName("닉네임이_같으면_true_를_반환한다")
        @Test
        void should_ReturnTrue_WhenNicknameIsSame() {
            //given
            Crew crew = new Crew("레오");

            //when
            boolean result = crew.equals(new Crew("레오"));

            //then
            assertThat(result).isTrue();
        }

        @DisplayName("닉네임이_다르면_false_를_반환한다")
        @Test
        void should_ReturnFalse_WhenNicknameIsNotSame() {
            //given
            Crew crew = new Crew("레오");

            //when
            boolean result = crew.equals(new Crew("오레오"));

            //then
            assertThat(result).isFalse();
        }
    }
}
