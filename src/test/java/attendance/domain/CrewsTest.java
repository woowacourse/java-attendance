package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
        Set<Crew> crewNames = Set.of(Crew.from(crewName1), Crew.from(crewName2), Crew.from(crewName3));
        //when
        Crews crews = new Crews(crewNames);

        //then
        Assertions.assertThat(crews.getCrews()).hasSize(3);
    }

    @Test
    void 크루들에서_크루이름_찾기() {
        //given
        String crewName1 = "빙봉";
        String crewName2 = "우가";
        String crewName3 = "밍티";
        Set<Crew> crewNames = Set.of(Crew.from(crewName1), Crew.from(crewName2), Crew.from(crewName3));
        Crews crews = new Crews(crewNames);

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
        Set<Crew> crewNames = Set.of(Crew.from(crewName1), Crew.from(crewName2), Crew.from(crewName3));
        Crews crews = new Crews(crewNames);

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
        Crew crew1 = Crew.from(crewName1);
        Crew crew2 = Crew.from(crewName2);
        Crew crew3 = Crew.from(crewName3);
        Set<Crew> crewNames = Set.of(crew1, crew2, crew3);
        Crews crews = new Crews(crewNames);

        LocalDate now = LocalDate.of(2025, 2, 19);

        LocalDateTime localDateTime = LocalDateTime.of(2025,2,19,10,31);
        // when
        Register register = new Register(crews, now);
        register.findInfo(crew1, localDateTime).isAbsence();
        // then
        Assertions.assertThat(register.findInfo(crew1, localDateTime).isAbsence()).isTrue();
    }

}