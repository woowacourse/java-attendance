package attendance;

import attendance.domain.Crews;
import attendance.domain.CrewsFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CrewsFactoryTest {

    @Test
    @DisplayName("파싱 결과를 받아 Crews 생성")
    void initFromCsvTest1() {
        assertThat(CrewsFactory.initFromCsv("src/test/resources/attendances.csv")).isInstanceOf(Crews.class);
    }

    @Test
    @DisplayName("파싱 결과를 받아 Crews 생성")
    void initFromCsvTest2() {
        Crews crews = CrewsFactory.initFromCsv("src/test/resources/attendances.csv");

        assertThat(crews.getCrews().size()).isEqualTo(4);
    }
}
