package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {
        @DisplayName("닉네임에 해당하는 크루를 반환한다.")
        @Test
        public void findByName() throws Exception {
            // given
            final var crewName = "우가";
            final var crew = new Crew(crewName);
            final var crews = new Crews(List.of(crew));

            // when
            final Crew actual = crews.findByName(crewName);

            // then
            assertThat(actual.getName()).isEqualTo(crewName);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {

        @DisplayName("닉네임에 해당하는 크루가 존재하지 않는다면, 예외가 발생한다.")
        @Test
        public void findByName() throws Exception {
            // given
            final var crewName = "우가";
            final var crews = new Crews(List.of());

            // when & then
            assertThatThrownBy(() -> {
                crews.findByName(crewName);
            }).isInstanceOf(IllegalArgumentException.class);

        }
    }
}
