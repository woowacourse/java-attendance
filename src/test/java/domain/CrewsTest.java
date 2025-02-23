package domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 크루_검색() {
        Crew may = new Crew("메이");
        Crew watermelon = new Crew("수박");
        Crews crews = new Crews(List.of(may, watermelon));

        Assertions.assertThat(crews.findCrew("메이")).isEqualTo(may);
    }

    @Test
    void 존재하지_않는_크루_검색_예외() {
        Crew may = new Crew("메이");
        Crew watermelon = new Crew("수박");
        Crews crews = new Crews(List.of(may, watermelon));

        Assertions.assertThatThrownBy(() -> crews.findCrew("에이프릴"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
