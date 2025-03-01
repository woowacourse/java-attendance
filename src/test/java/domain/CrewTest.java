package domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CrewTest {


    @Test
    void 크루_이름으로_크루를_생성한다() {
        // when
        Crew crew = Crew.fromName("제프리");

        // then
        Assertions.assertThat(crew.getName()).isEqualTo("제프리");
    }

    @Test
    void 크루_이름에_숫자가_들어오면_예외를_발생시킨다() {
        // given
        String name = "제프리1";

        // when // then
        Assertions.assertThatThrownBy(() -> Crew.fromName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루 이름을 정상적으로 입력해 주세요.");
    }

    @Test
    void 크루_이름에_단일_문자가_들어오면_예외를_발생시킨다() {
        // given
        String name = "제프ㅇ리";

        // when // then
        Assertions.assertThatThrownBy(() -> Crew.fromName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루 이름을 정상적으로 입력해 주세요.");
    }

    @Test
    void 크루_이름에_구분자가_들어오면_예외를_발생시킨다() {
        // given
        String name = "제프:리";

        // when // then
        Assertions.assertThatThrownBy(() -> Crew.fromName(name))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 크루 이름을 정상적으로 입력해 주세요.");
    }

}
