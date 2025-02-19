package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomLocalDateTimeTest {

    @DisplayName("커스텀 LocalDateTIme을 받아온다")
    @Test
    void test_getLocalDateTime() {
        assertThat(CustomLocalDateTime.now()).isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 0));
    }

}