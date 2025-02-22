package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 크루이름들로_크루들객체_생성() {
        //given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        List<String> crewNames = List.of(crewName1, crewName2, crewName3);
        //when
        Crews crews = Crews.fromCrewsFile(crewNames);

        //then
        Assertions.assertThat(crews.getCrews()).hasSize(3);
    }

    @Test
    void 크루들에서_크루이름_찾기() {
        //given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        List<String> crewNames = List.of(crewName1, crewName2, crewName3);
        Crews crews = Crews.fromCrewsFile(crewNames);

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
        List<String> crewNames = List.of(crewName1, crewName2, crewName3);
        Crews crews = Crews.fromCrewsFile(crewNames);

        //when & then
        Assertions.assertThatThrownBy(() -> crews.findCrew("제프리"))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NICKNAME_NOT_PRESENCE.getMessage());
    }

    @Test
    void 크루_출결_등록() {
        // given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        List<String> crewNames = List.of(crewName1, crewName2, crewName3);
        Crews crews = Crews.fromCrewsFile(crewNames);

        LocalDate now = LocalDate.of(2025, 2, 19);

        Map<Crew, AttendanceRegistry> register = new HashMap<>();

        // when
        crews.register(register, now);

        // then
        Assertions.assertThat(register.size()).isEqualTo(3);
    }

}