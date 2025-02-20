package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomLocalDateTimeTest {

    @DisplayName("커스텀 LocalDateTIme을 받아온다")
    @Test
    void test_getLocalDateTime() {
        assertThat(CustomLocalDateTime.now()).isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 0));
    }

    @Test
    void test7() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);

        Assertions.assertThat(CustomLocalDateTime.isHoliday(localDate)).isFalse();
    }

    @Test
    void test8() {
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        Assertions.assertThat(CustomLocalDateTime.isHoliday(localDate)).isTrue();
    }
}