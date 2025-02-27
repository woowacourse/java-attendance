package attendance.domain;

import attendance.util.ErrorMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

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
    void 크루_리스트에_크루가_이미존재한다면_덮어쓴다() {
        Crew duei = new Crew(new Nickname("듀이"));
        Crews crews = new Crews(new ArrayList<>());
        crews.add(duei);
        crews.add(duei);

        assertThat(crews.size()).isEqualTo(1);
    }

    @Test
    void 크루_리스트에_포함된_크루면_true_아니면_false를_반환한다() {
        Crew duei = new Crew(new Nickname("듀이"));
        Crews crews = new Crews(List.of(duei));
        Crew brown = new Crew(new Nickname("브라운"));

        assertAll(
                () -> assertThat(crews.contains(duei)).isTrue(),
                () -> assertThat(crews.contains(brown)).isFalse()
        );
    }
}
