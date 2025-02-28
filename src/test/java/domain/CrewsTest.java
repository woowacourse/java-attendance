package domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {
    @Test
    @DisplayName("존재하는 크루 닉네임이면 true를 반환한다")
    void existsByNickname_true() {
        // given
        String nickname = "부기";
        Crews crews = new Crews(List.of(nickname));

        // when & then
        Assertions.assertThat(crews.existsByNickname("부기")).isTrue();
    }

    @Test
    @DisplayName("존재하지 않는 크루 닉네임이면 false를 반환한다")
    void existsByNickname_false() {
        // given
        String nickname = "부기";
        Crews crews = new Crews(List.of(nickname));

        // when & then
        Assertions.assertThat(crews.existsByNickname("abc")).isFalse();
    }

    @Test
    @DisplayName("크루 리스트에 크루를 추가한다")
    void addCrewIfAbsent() {
        // given
        String newCrewNickname = "newCrew";
        Crew crew = new Crew(newCrewNickname);
        Crews crews = new Crews(List.of());
        crews.addCrewIfAbsent(crew);

        // when & then
        Assertions.assertThat(crews.existsByNickname(newCrewNickname)).isTrue();
    }

    @Test
    @DisplayName("크루 리스트에 null을 추가하려고 하면 예외가 발생한다")
    void addCrewIfAbsent_exception() {
        // given
        Crews crews = new Crews(List.of());
        Crew crew = null;

        // when & then
        Assertions.assertThatThrownBy(() -> {
            crews.addCrewIfAbsent(crew);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}