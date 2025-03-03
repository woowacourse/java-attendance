package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @Test
    @DisplayName("닉네임이 빈값이 아니면 크루를 생성할 수 있다")
    void new_success() {
        // given
        String nickname = "22";

        // when & then
        Assertions.assertThatCode(() -> {
            new Crew(nickname);
        }).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("닉네임이 빈값이면 예외가 발생한다")
    void new_exception() {
        // given
        String nickname = "";

        // when & then
        Assertions.assertThatThrownBy(() -> {
            new Crew(nickname);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("닉네임이 같으면 같은 Crew 객체다")
    void equals_test() {
        // given
        Crew crew = new Crew("히스타");
        Crew crew2 = new Crew("히스타");

        // when & then
        Assertions.assertThat(crew).isEqualTo(crew2);
    }
}
