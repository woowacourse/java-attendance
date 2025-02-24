package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

public class CrewTest {

    @Test
    void 닉네임을_가지고_크루를_생성한다() {
        String nickname = "빙봉";

        Crew crew = new Crew(nickname);

        assertThat(crew).isEqualTo(new Crew("빙봉"));
    }

    @ParameterizedTest
    @ValueSource(strings = {"가", "가나다라마"})
    void 닉네임_길이_범위를_벗어나면_크루가_생성되지_않는다(String nickname) {
        assertThatThrownBy(() -> new Crew(nickname))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @CsvSource(value = {
            "빙봉,true", "비보,false"
    })
    @ParameterizedTest
    void 닉네임을_알려주면_같은_이름인지_알려준다(String findNickname, boolean expected) {
        Crew crew = new Crew("빙봉");

        assertThat(crew.isSameNickName(findNickname)).isEqualTo(expected);
    }

}
