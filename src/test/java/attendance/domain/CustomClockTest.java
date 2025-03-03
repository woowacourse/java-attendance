package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomClockTest {

    @DisplayName("지정한 LocalDateTime으로 날짜가 반환된다.")
    @Test
    void testNowDate() {
        // given
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 12, 16, 10, 0);
        CustomClock clock = new CustomClock(fixedDateTime);
        LocalDate expectedDate = LocalDate.of(2024, 12, 16);

        // when
        LocalDate nowDate = clock.nowDate();

        // then
        assertThat(nowDate).isEqualTo(expectedDate);
    }

    @DisplayName("생성하고싶은 날짜를 받아서 고정 연도와 월에 해당하는 날짜를 반환한다.")
    @Test
    void testCreateDateFromDay() {
        // given
        LocalDateTime fixedDateTime = LocalDateTime.of(2024, 12, 16, 10, 0);
        CustomClock clock = new CustomClock(fixedDateTime);
        int day = 5;
        LocalDate expectedDate = LocalDate.of(2024, 12, day);

        // when
        LocalDate resultDate = clock.createDateFromDay(day);

        // then
        assertThat(resultDate).isEqualTo(expectedDate);

    }
}