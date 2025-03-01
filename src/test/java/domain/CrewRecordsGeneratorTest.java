package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CrewRecordsGeneratorTest {
    @DisplayName("크루 이름과 출석 일시 문자열을 받아 해당 크루에 대한 기록 객체를 생성할 수 있다.")
    @Test
    void generateTest() {
        // given
        List<String> attendances = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07", "빙티,2024-12-13 10:07");
        CrewRecordsGenerator crewRecordsGenerator = new CrewRecordsGenerator();
        CrewRecords actualValue = crewRecordsGenerator.generate(attendances);

        // then
        for (String attendance : attendances) {
            Crew crew = new Crew(attendance.split(",")[0]);
            LocalDateTime dateTime = LocalDateTime.parse(attendance.split(",")[1].replace(" ", "T"));

            assertThat(actualValue.getRecords().get(crew).hasRecordOnDate(dateTime.toLocalDate())).isTrue();
        }
    }
}
