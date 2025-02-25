package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CrewAttendancesTest {
    CrewAttendances crewAttendances = new CrewAttendances();

    @DisplayName("이미 출석부를 생성한 크루는 재생성할 수 없다.")
    @Test
    void test1() {
        // given
        Crew crew = new Crew("밍곰");
        crewAttendances.save(crew);

        // when & then
        assertThatThrownBy(() -> crewAttendances.save(crew))
                .isInstanceOf(RuntimeException.class);
    }
}
