
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AttendancesFileParserTest {

    @Test
    void 정해진_포맷대로_입력한_날짜를_LocalDateTime으로_변환한다() {
        AttendancesFileParser parser = new AttendancesFileParser();
        LocalDateTime formatter = parser.formatter("2024-12-13 10:08");
        assertThat(formatter).isEqualTo(LocalDateTime.of(2024, 12, 13, 10, 8));
    }
}
