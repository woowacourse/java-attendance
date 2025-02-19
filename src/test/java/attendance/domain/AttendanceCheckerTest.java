package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.utils.AttendanceChecker;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceCheckerTest {

    @ParameterizedTest
    @CsvSource(value = {"2024,12,13,10,0,출석", "2024,12,13,10,6,지각", "2024,12,13,10,31,결석",
            "2024,12,9,12,30,출석", "2024,12,9,13,6,지각", "2024,12,9,13,31,결석"})
    void 시간에_맞는_출결_상태를_갖는다(int year, int month, int day, int hour, int minute, String result) {

        // given
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, hour, minute);

        // when && then
        assertThat(AttendanceChecker.check(dateTime)).isEqualTo(result);
    }

    @DisplayName("주말에는 출석하지 않는다.")
    @Test
    void 주말에는_출석하지_않는다() {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 16, 10, 10);

        // when & then
        String message = String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                dateTime.getMonthValue(), dateTime.getDayOfMonth(), dateTime.getDayOfWeek().getDisplayName(
                        TextStyle.FULL, Locale.KOREAN));
        assertThatThrownBy(() -> AttendanceChecker.check(dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(message);
    }

    @DisplayName("캠퍼스 운영 시간에만 출석한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "7,59",
            "23,1"
    }, delimiter = ',')
    void 캠퍼스_운영_시간에만_출석한다(int hour, int minute) {

        // given
        LocalDateTime dateTime = LocalDateTime.of(2025, 2, 19, hour, minute);

        // when & then
        assertThatThrownBy(() -> AttendanceChecker.check(dateTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 출석 가능한 시간이 아닙니다.");
    }
}