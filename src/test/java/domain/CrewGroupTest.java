package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewGroupTest {

    @Test
    @DisplayName("이름으로 크루를 찾는다.")
    void test1() {
        //given
        final long id = 1L;
        final String name = "윌슨";
        Map<String, Crew> crewMap = Map.of(name, new Crew(id, name, new LinkedHashMap<>()));

        //when
        final CrewGroup crewGroup = new CrewGroup(crewMap);
        final Crew crew = crewGroup.findByName(name);

        //then
        assertThat(crew.getName()).isEqualTo(name);
    }

    @Test
    @DisplayName("존재하지 않는 크루이므로 예외가 발생한다.")
    void test2() {
        //given
        final String name = "윌슨";

        //when
        final CrewGroup crewGroup = new CrewGroup(new HashMap<>());

        //then
        assertThatIllegalArgumentException().isThrownBy(() -> crewGroup.findByName(name));
    }

}
