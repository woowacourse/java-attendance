package domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @DisplayName("크루와 동일한 이름이라면 true를 반환한다.")
    @Test
    void isSame() {
        //given
        Crew crew = Crew.from("도기");
        String name = "도기";

        //when
        boolean actual = crew.isSame(name);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("크루와 동일한 이름이라면 false를 반환한다.")
    @Test
    void isNotSame() {
        //given
        Crew crew = Crew.from("도기");
        String name = "포비";

        //when
        boolean actual = crew.isSame(name);

        //then
        assertThat(actual).isFalse();
    }
}
