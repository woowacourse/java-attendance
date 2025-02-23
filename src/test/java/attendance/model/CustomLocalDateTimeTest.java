package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import global.BaseTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class CustomLocalDateTimeTest extends BaseTest {

    @Test
    void 커스텀_LocalDateTime을_받아온다() {
        assertThat(CustomLocalDateTime.now()).isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 0));
    }

    @Test
    void 커스텀_LocalDate를_받아온다() {
        assertThat(CustomLocalDateTime.nowDate()).isEqualTo(LocalDate.of(2024, 12, 16));
    }

}
