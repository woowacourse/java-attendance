package attendance.util;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import attendance.domain.Crew;

class AttendanceParserTest {

    @Test
    @DisplayName("문자열을 파싱해 크루 정보로 변환한다")
    void parseLineTest() {
        // given
        String line = "쿠키,2024-02-03 10:31";
        AttendanceParser.CrewData expected = new AttendanceParser.CrewData(
            "쿠키",
            LocalDate.of(2024, 2, 3),
            LocalTime.of(10, 31));

        // when
        AttendanceParser.CrewData crewData = AttendanceParser.parseLine(line);

        // then
        assertThat(crewData).usingRecursiveAssertion().isEqualTo(expected);
    }

    @Test
    @DisplayName("여러 라인을 불러와 크루 정보로 변환한다")
    void parseLinesTest() {
        // given
        Stream<String> lines = """
            쿠키,2025-02-03 10:08
            쿠키,2025-02-04 10:07
            쿠키,2025-02-05 10:06
            쿠키,2025-02-06 10:05
            """.lines();

        // when
        List<Crew> crews = AttendanceParser.parseLines(lines);

        // then
        Crew actual = crews.get(0);
        assertThat(actual.getName()).isEqualTo("쿠키");
        assertThat(actual.getAttendanceTimeOf(LocalDate.of(2025, 2, 3))).isEqualTo(LocalTime.of(10, 8));
        assertThat(actual.getAttendanceTimeOf(LocalDate.of(2025, 2, 4))).isEqualTo(LocalTime.of(10, 7));
        assertThat(actual.getAttendanceTimeOf(LocalDate.of(2025, 2, 5))).isEqualTo(LocalTime.of(10, 6));
        assertThat(actual.getAttendanceTimeOf(LocalDate.of(2025, 2, 6))).isEqualTo(LocalTime.of(10, 5));
    }
}
