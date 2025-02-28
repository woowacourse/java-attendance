import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    @DisplayName("등록된 모든 크루들을 반환한다")
    void test1() {
        // given
        Crews crews = new Crews(List.of(
                new Crew("히로"),
                new Crew("히포"),
                new Crew("히스타")
        ));

        // when & then 
        assertThat(crews.getAll()).hasSize(3);
    }
}
