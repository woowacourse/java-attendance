package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewsTest {

    @Test
    @DisplayName("제적 위험자인 크루들을 반환한다")
    void getRiskCrewsTest() {
        // given
        Crews crews = Crews.generate();
        crews.add(new Crew("pobi"));
        crews.add(new Crew("moko"));
        crews.add(new Crew("dogi"));

        // when
        crews.get("pobi").attendance(LocalDate.of(2025, 2, 3), LocalTime.of(10, 00));

        // then
        List<Crew> riskCrews = crews.getRiskCrews(LocalDate.of(2025, 2, 5));
        assertThat(riskCrews.get(0).getName()).isEqualTo("moko");
        assertThat(riskCrews.get(1).getName()).isEqualTo("dogi");
    }
}
