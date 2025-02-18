package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.Crew;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("크루를 생성한다.")
    @Test
    void test_Crew() {
        String name = "멍구";
        Crew crew = new Crew(name);

        assertThat(crew).isNotNull();
    }

    @DisplayName("생성된 크루의 이름을 확인한다.")
    @Test
    void test_CrewName() {
        String name = "멍구";
        Crew crew = new Crew(name);

        assertThat(crew.name()).isEqualTo(name);
    }

    @DisplayName("크루의 이름이 5자 이내가 아니라면 예외가 발생한다.")
    @Test
    void error_CrewName2() {
        String name = "123456";

        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("크루의 이름이 공백인 경우 예외가 발생한다.")
    @Test
    void error_CrewName3() {
        String name = " ";

        assertThatThrownBy(() -> new Crew(name))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
