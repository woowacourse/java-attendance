package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CrewsTest {

    private Crews crews;

    @BeforeEach
    void setUp() {
        crews = new Crews();
    }

    @Test
    void 크루를_추가할_수_있다() {
        Crew crew = new Crew("크루");

        crews.add(crew);

        Crew foundCrew = crews.getByNickName("크루");
        assertThat(foundCrew).isEqualTo(crew);
    }

    @Test
    void 닉네임에_해당하는_크루가_없으면_예외가_발생한다() {
        Crew crew = new Crew("크루");
        crews.add(crew);

        assertThatThrownBy(() -> crews.getByNickName("크루루"))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 모든_크루를_가져올_수_있다() {
        Crew crew1 = new Crew("크루1");
        Crew crew2 = new Crew("크루2");
        crews.add(crew1);
        crews.add(crew2);

        List<Crew> allCrews = crews.findAll();

        assertThat(allCrews)
            .hasSize(2)
            .containsExactlyElementsOf(List.of(crew1, crew2));
    }
}
