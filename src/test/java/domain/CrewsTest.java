package domain;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {
        @Test
        @DisplayName("크루 리스트에 크루를 추가한다")
        void addCrewIfAbsent_test() {
            // given
            String newCrewNickname = "newCrew";
            Crew crew = new Crew(newCrewNickname);
            Crews crews = new Crews(new ArrayList<>());
            crews.addCrewIfAbsent(crew);

            // when & then
            Assertions.assertThat(crews.existsByNickname(newCrewNickname)).isTrue();
        }

        @Test
        @DisplayName("존재하는 크루 닉네임이면 true를 반환한다")
        void existsByNickname_test_true() {
            // given
            String nickname = "부기";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            // when & then
            Assertions.assertThat(crews.existsByNickname("부기")).isTrue();
        }

        @Test
        @DisplayName("존재하지 않는 크루 닉네임이면 false를 반환한다")
        void existsByNickname_test_false() {
            // given
            String nickname = "부기";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            // when & then
            Assertions.assertThat(crews.existsByNickname("abc")).isFalse();
        }

        @Test
        @DisplayName("닉네임으로 크루를 찾는다")
        void findByNickname_test() {
            // given
            String nickname = "부기";
            Crew crew = new Crew(nickname);
            Crews crews = new Crews(List.of(crew));

            // when & then
            Assertions.assertThat(crews.findByNickname(nickname)).isEqualTo(crew);
        }
    }


    @Nested
    @DisplayName("예외 테스트")
    class Fail {
        @Test
        @DisplayName("크루 리스트에 null을 추가하려고 하면 예외가 발생한다")
        void addCrewIfAbsent_test_exception() {
            // given
            Crews crews = new Crews(List.of());
            Crew crew = null;

            // when & then
            Assertions.assertThatThrownBy(() -> {
                crews.addCrewIfAbsent(crew);
            }).isInstanceOf(IllegalArgumentException.class);
        }
    }
}