package domain;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewGroupTest {
    @DisplayName("중복되는 이름은 허용되지 않는다.")
    @Test
    void test() {
        List<String> crews = List.of("수양", "수양");
        Assertions.assertThatThrownBy(() -> CrewGroup.from(crews))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("");
    }

    @DisplayName("크루 이름으로 크루목록에서 조회한다")
    @Test
    void test2() {
        String crewName = "가이온";
        List<String> crews = List.of("수양", crewName);
        CrewGroup crewGroup = CrewGroup.from(crews);

        Crew crew = crewGroup.findCrew(crewName);

        Assertions.assertThat(crew).isInstanceOf(Crew.class);
        Assertions.assertThat(crew.getName()).isEqualTo(crewName);
    }
}
