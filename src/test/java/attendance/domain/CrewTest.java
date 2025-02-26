package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @DisplayName("기능: 이름으로 Crew 인스턴스의 일치 여부를 비교")
    @Test
    void checkSameNameCrewInstance() {
        Crew crew1 = new Crew("엠제이");

        assertThat(crew1.isSameCrewName("엠제이")).isTrue();
    }

    @DisplayName("기능: 이름이 동일한 다른 크루 객체는 같은 크루로 비교")
    @Test
    void checkSameNameCrew() {
        Crew crew1 = new Crew("리원");
        Crew crew2 = new Crew("리원");

        assertThat(crew1).isEqualTo(crew2);
    }
}
