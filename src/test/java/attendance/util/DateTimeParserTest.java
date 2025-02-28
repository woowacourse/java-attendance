package attendance.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class DateTimeParserTest {

    @Test
    @DisplayName("특정 날짜와 시간을 파싱해 반환한다")
    void 특정_날짜와_시간을_파싱해_반환한다() {
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
