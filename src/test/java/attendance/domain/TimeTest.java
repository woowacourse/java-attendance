package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeTest {

    @DisplayName("캠퍼스 운영 시간에만 출석한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "07,59",
            "23,01"
    }, delimiter = ',')
    void 캠퍼스_운영_시간에만_출석한다(String hour, String minute) {

        // given
        LocalDate localDate = LocalDate.of(2025, 2, 19);

        // when & then
        assertThatThrownBy(() -> new Time(localDate, hour, minute, false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 가능한 시간이 아닙니다.");
    }

    @DisplayName("주말에는 출석하지 않는다.")
    @Test
    void 주말에는_출석하지_않는다() {

        // given
        LocalDate localDate = LocalDate.of(2025, 2, 15);

        // when & then
        assertThatThrownBy(() -> new Time(localDate, "10", "10", false))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 02월 15일 토요일은 등교일이 아닙니다.");
    }

    @DisplayName("기준이 이후 시간이면 false를, 아니면 true를 반환한다.")
    @ParameterizedTest
    @CsvSource(value = {"2025,02,24,13,00, false", "2025,02,24,13,10, true"})
    void 기준보다_이후_시간이면_false를_아니면_true를_반환한다(int year, int month, int day, int hour, int minute, boolean result) {

        // given
        LocalDateTime localDateTime = LocalDateTime.of(year, month, day, hour, minute);
        Time standardTime = new Time(LocalDate.of(2025, 2, 24), "13", "5", false);

        // when
        boolean isAfter = standardTime.isBefore(localDateTime);

        // then
        assertThat(isAfter).isEqualTo(result);
    }

}
