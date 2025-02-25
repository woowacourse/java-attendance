package attendance.domain;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 초기_크루들_객체_생성() {
        // given
        Crews crews = Crews.initCrews();
        // when // then
        Assertions.assertThat(crews).isNotNull();
        Assertions.assertThat(crews.getCrews()).hasSize(0);
    }

    @Test
    void 크루이름들로_크루들객체_생성() {
        // given
        Crews crews = Crews.initCrews();
        crews.addCrew(Crew.from("빙봉"));
        crews.addCrew(Crew.from("우가"));
        crews.addCrew(Crew.from("밍티"));

        // when // then
        Assertions.assertThat(crews.getCrews()).hasSize(3);
    }


    @Test
    void 크루들에서_크루이름_찾기() {
        // given
        Crews crews = Crews.initCrews();
        crews.addCrew(Crew.from("빙봉"));
        crews.addCrew(Crew.from("우가"));
        crews.addCrew(Crew.from("밍티"));

        // when
        Crew crew = crews.findCrew("우가");

        // then
        Assertions.assertThat(crew.getCrewName()).isEqualTo("우가");
    }

    @Test
    void 크루들에서_크루이름_찾기_실패() {
        //given
        Crews crews = Crews.initCrews();
        crews.addCrew(Crew.from("빙봉"));
        crews.addCrew(Crew.from("우가"));
        crews.addCrew(Crew.from("밍티"));

        // when // then
        Assertions.assertThatThrownBy(() -> crews.findCrew("제프리"))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NICKNAME_NOT_PRESENCE.getMessage());
    }

    @Test
    void 크루가_존재하면_true_반환() {
        // given
        Crews crews = Crews.initCrews();
        crews.addCrew(Crew.from("빙봉"));
        crews.addCrew(Crew.from("우가"));
        crews.addCrew(Crew.from("밍티"));

        // when // then
        Assertions.assertThat(crews.hasCrew("빙봉")).isTrue();
    }

    @Test
    void 크루가_존재하지_않으면_false_반환() {
        // given
        Crews crews = Crews.initCrews();
        crews.addCrew(Crew.from("빙봉"));
        crews.addCrew(Crew.from("우가"));
        crews.addCrew(Crew.from("밍티"));

        // when // then
        Assertions.assertThat(crews.hasCrew("제프리")).isFalse();
    }

}
