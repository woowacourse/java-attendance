package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("크루 그룹 테스트")
class CrewGroupTest {

    @DisplayName("같은 닉네임을 가진 크루가 있는지 확인할 수 있다.")
    @Test
    void containsTest() {
        // given
        Nickname nickname = new Nickname("포비");
        Crew crew = new Crew(nickname);
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));

        // when & then
        assertThat(crewGroup.contains(nickname))
                .isTrue();
    }
}
