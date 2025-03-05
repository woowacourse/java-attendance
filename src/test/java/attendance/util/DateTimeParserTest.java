package attendance.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("날짜와 시간 파싱 테스트")
class DateTimeParserTest {

    @Test
    @DisplayName("특정 날짜와 시간을 파싱해 반환한다")
    void parseAndReturnSpecificDateTime() {
        // given
        String inputDateTime = "2025-02-12 10:00";
        LocalDateTime excepted = LocalDateTime.of(2025, 2, 12, 10, 0);

        // when
        LocalDateTime result = DateTimeParser.parseDateTime(inputDateTime);

        // then
        assertThat(result)
                .isEqualTo(excepted);
    }
}
