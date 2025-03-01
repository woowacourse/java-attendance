package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @DisplayName("Crew는 닉네임을 가진다.")
    @Test
    void create() {
        //given
        String name = "도기";

        //when
        Crew crew = Crew.of(name);

        //then
        assertThat(crew.getName()).isEqualTo("도기");
    }

    @DisplayName("닉네임이 없다면 예외가 발생한다.")
    @Test
    void notNickName() {
        //given
        String name = "";

        //when

        //then
        assertThatThrownBy(() -> Crew.of(name)).isInstanceOf(IllegalArgumentException.class).hasMessage("닉네임은 필수 입니다.");
    }
}
