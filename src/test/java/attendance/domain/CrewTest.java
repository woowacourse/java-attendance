package attendance.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
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
}