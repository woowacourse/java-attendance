package util;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DateTimeGeneratorTest {

    LocalDate fixedDate;
    DateTimeGenerator dateTimeGenerator;

    @BeforeEach
    void beforeEach() {
        fixedDate = LocalDate.of(2025, 2, 28);
        FixedDateTimeStrategy fixedDateTimeStrategy = new FixedDateTimeStrategy(fixedDate);
        dateTimeGenerator = new DateTimeGenerator(fixedDateTimeStrategy);
    }

    @Test
    @DisplayName("현재 시간을 가져온다.")
    void test1() {
        // given

        // when
        LocalDate now = dateTimeGenerator.now();

        // then
        Assertions.assertThat(now).isEqualTo(fixedDate);
    }

    @Test
    @DisplayName("현재 LocalDate 시간을 가져온다.")
    void test2() {
        // given

        // when
        LocalDate localDate = dateTimeGenerator.now();

        // then
        Assertions.assertThat(localDate).isEqualTo(fixedDate);
    }
}
