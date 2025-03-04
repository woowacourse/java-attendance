package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewNameTest {
    @DisplayName("정상: 크루 이름이 같을 시 동일한 크루 객체로 확인")
    @Test
    void successExecution() {
        CrewName crewName = new CrewName("초코");
        CrewName sameCrewName = new CrewName("초코");

        assertThat(crewName).isEqualTo(sameCrewName);
    }
}
