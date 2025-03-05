package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.List;

import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    void 닉네임_목록이_존재하지_않으면_크루_목록을_생성할_수_없다() {
        assertThatThrownBy(() -> new Crews(List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루의 닉네임이 존재하지 않습니다.");
    }

    @Test
    void 중복된_닉네임이_존재하면_크루_목록을_생성할_수_없다() {
        assertThatThrownBy(() -> new Crews(List.of("빙봉", "쿠키", "빙봉")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("크루의 닉네임은 중복될 수 없습니다.");
    }

    @Test
    void 닉네임_목록을_알려주면_크루_목록을_생성한다() {
        assertDoesNotThrow(() -> new Crews(List.of("빙봉", "쿠키")));
    }

    @Test
    void 닉네임을_알려주면_해당_크루를_조회한다() {
        Crews crews = new Crews(List.of("빙봉", "쿠키"));

        assertThat(crews.findCrewByNickname("빙봉")).isEqualTo(new Crew("빙봉"));
    }

}
