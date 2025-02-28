package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static util.parser.DateTimeParser.parseStringToDate;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@Nested
public class HolidayTest {

    @Nested
    @DisplayName("휴일 테스트")
    class CheckQuitTest {

        @Test
        @DisplayName("평일을 확인한다.")
        void checkWeekday() {
            LocalDate date = parseStringToDate("2024-12-04");
            assertThat(Holiday.isHoliday(date)).isFalse();
        }

        @Test
        @DisplayName("주말을 확인한다.")
        void checkWeekend() {
            LocalDate date = parseStringToDate("2024-12-08");
            assertThat(Holiday.isHoliday(date)).isTrue();
        }

        @Test
        @DisplayName("공휴일을 확인한다.")
        void checkHoliday() {
            LocalDate date = parseStringToDate("2024-12-25");
            assertThat(Holiday.isHoliday(date)).isTrue();
        }
    }
}