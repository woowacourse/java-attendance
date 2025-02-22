package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.util.Set;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 크루이름들로_크루들객체_생성() {
        //given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        Set<String> tempCrews = Set.of(crewName1, crewName2, crewName3);

        //when
        Crews crews = Crews.fromCrewNames(tempCrews);

        //then
        Assertions.assertThat(crews.getCrews()).hasSize(3);
    }


    @Test
    void 크루들에서_크루이름_찾기() {
        //given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        Set<String> tempCrews = Set.of(crewName1, crewName2, crewName3);
        Crews crews = Crews.fromCrewNames(tempCrews);

        //when
        Crew crew = crews.findCrew("우가");

        //then
        Assertions.assertThat(crew.getCrewName()).isEqualTo("우가");
    }

    @Test
    void 크루들에서_크루이름_찾기_실패() {
        //given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        Set<String> tempCrews = Set.of(crewName1, crewName2, crewName3);
        Crews crews = Crews.fromCrewNames(tempCrews);

        //when & then
        Assertions.assertThatThrownBy(() -> crews.findCrew("제프리"))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NICKNAME_NOT_PRESENCE.getMessage());
    }

}
