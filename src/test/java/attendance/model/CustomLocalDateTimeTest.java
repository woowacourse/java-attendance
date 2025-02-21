package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CustomLocalDateTimeTest {

    @Test
    void 커스텀_LocalDateTIme을_받아온다() {
        assertThat(CustomLocalDateTime.now()).isEqualTo(LocalDateTime.of(2024, 12, 16, 12, 0));
    }

    @Test
    void 평일은_쉬는날이_아니다() {
        LocalDate localDate = LocalDate.of(2024, 12, 10);
        assertThat(CustomLocalDateTime.isHoliday(localDate)).isFalse();
    }

    @Test
    void 공휴일로_지정된_날짜는_쉬는날이다() {
        LocalDate localDate = LocalDate.of(2024, 12, 25);
        assertThat(CustomLocalDateTime.isHoliday(localDate)).isTrue();
    }

    @Test
    void 주말은_쉬는날이다() {
        LocalDate localDate = LocalDate.of(2024, 12, 8);
        assertThat(CustomLocalDateTime.isHoliday(localDate)).isTrue();
    }

    @DisplayName("HH:mm형태의 문자열 시간을 LocalTime으로_파싱한다")
    @Test
    void 문자열_시간을_LocalTime으로_파싱한다() {
        assertThat(CustomLocalDateTime.parseTime("09:58")).isEqualTo(LocalTime.of(9,58));
    }

    @Test
    void 올바르지_않은_시간을_파싱하면_예외가_발생한다() {
        assertThatThrownBy(() -> CustomLocalDateTime.parseTime("28:58"));
    }
}
