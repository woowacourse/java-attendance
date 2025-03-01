package util;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeGeneratorTest {

    LocalDateTime fixedDateTime;
    DateTimeGenerator dateTimeGenerator;

    @BeforeEach
    void beforeEach() {
        fixedDateTime = LocalDateTime.of(2025, 2, 28, 12, 0);
        FixedDateTimeStrategy fixedDateTimeStrategy = new FixedDateTimeStrategy(fixedDateTime);
        dateTimeGenerator = new DateTimeGenerator(fixedDateTimeStrategy);
    }

    @Test
    @DisplayName("현재 시간을 가져온다.")
    void test1() {
        // given

        // when
        LocalDateTime now = dateTimeGenerator.now();

        // then
        Assertions.assertThat(now).isEqualTo(fixedDateTime);
    }
}
