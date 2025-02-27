package attendance.domain;

import attendance.util.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("크루 리스트 테스트")
public class CrewsTest {

    @Test
    void 크루_리스트에_크루를_추가할_수_있다() {
        Crews crews = new Crews(new ArrayList<>());
        Crew crew = new Crew(new Nickname("듀이"));
        crews.add(crew);

        assertThat(crews.size()).isEqualTo(1);
    }

    @Test
    void 크루_리스트에_크루가_이미존재한다면_예외를_반환한다() {
        Crew duei = new Crew(new Nickname("듀이"));
        Crews crews = new Crews(List.of(duei));

        assertThatThrownBy(() -> crews.add(duei))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ErrorMessage.CREW_DUPLICATE_ERROR.getMessage());
    }
}
