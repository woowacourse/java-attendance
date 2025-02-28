package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CrewsFactoryTest {
    LocalDate today = LocalDate.of(2024, 12, 16);

    @Test
    @DisplayName("파싱 결과를 받아 Crews 생성")
    void initFromCsvTest1() {
        assertThat(CrewsFactory.initFromCsv("src/test/resources/attendances.csv", today)).isInstanceOf(Crews.class);
    }

    @Test
    @DisplayName("파싱 결과를 받아 Crews 생성")
    void initFromCsvTest2() {
        Crews crews = CrewsFactory.initFromCsv("src/test/resources/attendances.csv", today);

        assertThat(crews.getCrews().size()).isEqualTo(4);
    }

    @Test
    @DisplayName("Csv 파일에 없는 날짜는 결석으로")
    void initFromCsvTest3() {
        Crews crews = CrewsFactory.initFromCsv("src/test/resources/attendances.csv", today);
        assertThat(crews.findCrewByNickname("쿠키").getAttendances().size()).isEqualTo(10);
    }
}
