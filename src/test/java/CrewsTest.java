import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {
    @Test
    @DisplayName("존재하지 않는 크루인 경우 예외를 던진다")
    void testCrewThrowsExceptionWhenCrewDoesNotExist() {
        // given
        String name = "없음";
        Crews crews = new Crews(new ArrayList<>());

        // when & then
        assertThatThrownBy(() -> crews.findCrewByName(name))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
