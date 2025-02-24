package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("크루 그룹 테스트")
class CrewGroupTest {

    @DisplayName("닉네임을 이용해 크루를 찾을 수 있다.")
    @Test
    void findCrewByNickname() {
        String nickname = "포비";
        Crew crew = new Crew(nickname);
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));

        assertThat(crewGroup.findCrewByNickname(nickname))
                .isEqualTo(new Crew("포비"));
    }

    @DisplayName("찾으려 하는 닉네임이 없는 경우 예외가 발생한다.")
    @Test
    void shouldThrowException_WhenUseNotExistNickname() {
        Crew crew = new Crew("포비");
        CrewGroup crewGroup = new CrewGroup(Set.of(crew));

        String notExistNickname = "네오";
        Assertions.assertThatThrownBy(() -> crewGroup.findCrewByNickname(notExistNickname))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("네오은(는) 등록되지 않은 닉네임입니다.");
    }
}
