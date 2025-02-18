import static org.assertj.core.api.Assertions.assertThatCode;

import domain.Crew;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    void 크루는_닉네임을_가질수_있다() {
        assertThatCode(() -> new Crew("name"))
            .doesNotThrowAnyException();
    }
}
