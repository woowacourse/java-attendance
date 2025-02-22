package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewTest {

    @DisplayName("이름이 같으면 동일한 크루로 취급한다.")
    @Test
    void 이름이_같으면_동일한_크루로_취급한다() {
        Crew crew = new Crew("쿠키");
        Crew otherCrew = new Crew("쿠키");

        assertThat(crew).isEqualTo(otherCrew);
    }

    @DisplayName("크루 이름은 공백을 허용하지 않는다.")
    @Test
    void 크루_이름은_공백을_허용하지_않는다() {
        Crew crew = new Crew(" 쿠 키 ");
        Crew otherCrew = new Crew("쿠키");

        assertThat(crew).isEqualTo(otherCrew);
    }

    @DisplayName("입력된 이름이 현재 크루 이름과 동일한지 확인한다.")
    @Test
    void 입력된_이름이_현재_크루_이름과_동일한지_확인한다() {
        String sameName = "쿠키";
        String notSameName = "빙봉";
        Crew crew = new Crew("쿠키");

        assertThat(crew.isSameName(sameName)).isTrue();
        assertThat(crew.isSameName(notSameName)).isFalse();
    }
}