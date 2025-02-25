package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import global.BaseTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CustomLocalDateTimeTest extends BaseTest {

    @Test
    void 커스텀_LocalDateTime을_받아온다() {
        // given

        // when
        LocalDateTime result = CustomLocalDateTime.now();

        // then
        assertThat(result).isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 0));
    }

    @Test
    void 커스텀_LocalDate를_받아온다() {
        // given

        // when
        LocalDate result = CustomLocalDateTime.nowDate();

        // then
        assertThat(result).isEqualTo(LocalDate.of(2024, 12, 16));
    }
}
