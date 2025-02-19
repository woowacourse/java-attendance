package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewNameTest {

    @Test
    @DisplayName("크루 이름 객체 생성을 테스트 한다.")
    void nameTest1() {
        //given
        final String name = "윌슨";

        //when
        final CrewName crewName = new CrewName(name);
        final String name1 = crewName.getName();

        //then
        assertThat(name1).isEqualTo(name);

    }

    @Test
    @DisplayName("크루 이름을 객체 매칭을 테스트 한다.")
    void nameTest2() {
        //given
        final String name = "윌슨";
        final String inputName = "부기";
        final CrewName crewName = new CrewName(name);

        //when
        final boolean sameName = crewName.isSameName(inputName);

        //then
        assertThat(sameName).isFalse();

    }

}
