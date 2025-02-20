package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewTest {

    @Test
    void 크루_이름을_바탕으로_객체_생성() {
        //given
        Crew crew = Crew.from("우가");

        //when //then
        Assertions.assertThat(crew.getCrewName()).isEqualTo("우가");
    }

    @Test
    void 동일한_이름_검사_참값_반환(){
        //given
        String crewName1 = "밍티";
        String crewName2 = "밍티";
        Crew crew1 = Crew.from(crewName1);

        //when
        boolean result = crew1.checkSameName(crewName2);
        //then
        Assertions.assertThat(result).isTrue();
    }

    @Test
    void 동일한_이름_검사_거짓_반환() {
        //given
        String crewName1 = "밍티";
        String crewName2 = "빙봉";
        Crew crew1 = Crew.from(crewName1);

        //when
        boolean result = crew1.checkSameName(crewName2);
        //then
        Assertions.assertThat(result).isFalse();
    }

}