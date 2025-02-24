package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


public class TodayDateTimeGeneratorTest {

    @Nested
    @DisplayName("성공 테스트")
    class SuccessCases {

        @DisplayName("2024년 12월 13일로 지정된 날짜를 생성한다.")
        @Test
        public void generateDate() throws Exception {
            // given
            final TodayDateTimeGenerator todayDateTimeGenerator = new TodayDateTimeGenerator();

            // when
            final LocalDate actual = todayDateTimeGenerator.generateDate();

            // then
            assertThat(actual)
                    .hasYear(2024)
                    .hasMonth(Month.DECEMBER)
                    .hasDayOfMonth(13);
        }
    }
}
