package attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceParserTest {

    @Test
    @DisplayName("문자열을 파싱해 크루 정보로 변환한다")
    void parseLineTest() {
        // given
        String line = "쿠키,2024-02-03 10:31";

        // when
        List<Crew> crews = AttendanceParser.parseLine(line);

        // then
        assertThat(crews.get(0).getAttendanceTimeOf(LocalDate.of(2024, 2, 3))).isEqualTo(LocalTime.of(10, 31));
    }
}
