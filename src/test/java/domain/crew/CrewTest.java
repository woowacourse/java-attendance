package domain.crew;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @Test
    @DisplayName("크루 이름 일치 확인 기능 테스트")
    void 크루_이름_일치_확인_기능_테스트() {
        // given
        String crewName = "쿠키";
        Crew crew = new Crew(crewName);
        // when & then
        assertTrue(crew.isCrew(crewName));
    }
}
