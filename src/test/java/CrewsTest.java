import static org.assertj.core.api.Assertions.assertThat;

import domain.Crew;
import domain.Crews;
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
            final var crews = new Crews();
            final var crewName = "우가";

            // when
            final Crew actual = crews.findByName(crewName);

            // then
            assertThat(actual.getName()).isEqualTo(crewName);
        }
    }

    @Nested
    @DisplayName("실패 테스트")
    class FailCases {
    }
}
