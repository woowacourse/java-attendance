package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceParserTest {

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
}
