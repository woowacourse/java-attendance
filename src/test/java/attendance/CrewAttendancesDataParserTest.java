package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.util.CrewAttendancesDataParser;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class CrewAttendancesDataParserTest {

    @Test
    void 입력된_값을_가공해_AttendanceBook을_반환한다() {
        String input = "쿠키,2024-12-13 10:08";
        final var result = CrewAttendancesDataParser.parse(input);

        assertThat(result.get("쿠키").getFirst()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
    }

    @Test
    void 입력된_값을_가공해_AttendanceBook을_반환한다2() {
        String input = "쿠키,2024-12-13 10:08\n"
                + "빙봉,2024-12-13 10:07";
        final var result = CrewAttendancesDataParser.parse(input);

        assertThat(result.get("빙봉").getFirst()).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 7));
    }

    @Test
    void 입력값을_LocalDateTime으로_변환한다() {
        String input = "2024-12-13 10:08";
        final var result = CrewAttendancesDataParser.parseLocalDateTime(input);

        assertThat(result).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
    }
}
