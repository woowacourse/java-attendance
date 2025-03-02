package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

class CrewRecordsGeneratorTest {
    @DisplayName("크루 이름과 출석 일시 문자열을 받아 해당 크루에 대한 기록 객체를 생성할 수 있다.")
    @Test
    void generateTest() {
        // given
        LocalDate currentDate = LocalDate.of(2024, 12, 14);
        List<String> attendances = List.of("쿠키,2024-12-13 10:08", "빙봉,2024-12-13 10:07", "빙티,2024-12-13 10:07");
        CrewRecordsGenerator crewRecordsGenerator = new CrewRecordsGenerator();

        // when
        CrewRecords crewRecords = crewRecordsGenerator.generate(currentDate, attendances);

        // then
        for (String attendance : attendances) {
            Crew crew = new Crew(attendance.split(",")[0]);
            LocalDateTime dateTime = LocalDateTime.parse(attendance.split(",")[1].replace(" ", "T"));

            assertThatNoException().isThrownBy(() -> crewRecords.getRecordOnDate(crew, dateTime.toLocalDate()));
        }
    }

    @DisplayName("등교일에 출석 기록이 없을 경우 결석 기록을 자동으로 생성한다.")
    @Test
    void generateAbsenceTest() {
        // given
        LocalDate currentDate = LocalDate.of(2024, 12, 5);
        List<String> attendances = List.of("쿠키,2024-12-02 13:08", "쿠키,2024-12-04 10:00");
        Crew crew = new Crew("쿠키");
        CrewRecordsGenerator crewRecordsGenerator = new CrewRecordsGenerator();

        // when
        LocalDate missingDate = LocalDate.of(2024, 12, 3);
        AttendanceRecord expectedValue = new AttendanceRecord(LocalDateTime.parse("2024-12-03T23:59"));
        CrewRecords crewRecords = crewRecordsGenerator.generate(currentDate, attendances);

        // then
        assertThat(crewRecords.getRecordOnDate(crew, missingDate)).isEqualTo(expectedValue);
    }
}
