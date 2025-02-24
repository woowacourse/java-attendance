package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("출석 상태를 판별")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AttendanceCheckerTest {

    @ParameterizedTest
    @CsvSource({
            "6, 0",
            "7, 59",
            "23, 1"
    })
    void 캠퍼스의_운영_시간이_아니면_예외가_발생한다(int hour, int minute) {
        assertThatThrownBy(() -> AttendanceChecker.checkCampusHour(hour, minute))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 현재 캠퍼스 운영시간이 아닙니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "8, 0",
            "12, 0",
            "18, 0",
            "23, 0"
    })
    void 캠퍼스의_운영_시간이면_예외가_발생하지_않는다(int hour, int minute) {
        assertThatCode(() -> AttendanceChecker.checkCampusHour(hour, minute)).doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(ints = {25, 22, 29, 21, 28})
    void 캠퍼스가_휴일이면_예외가_발생한다(int day) {
        String displayName = LocalDate.of(2024, 12, day).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA);

        assertThatThrownBy(() -> AttendanceChecker.validateCampusDay(day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("[ERROR] 12월 %02d일 %s은 등교일이 아닙니다.", day, displayName));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 24, 20, 23})
    void 캠퍼스가_운영일이면_예외가_발생하지_않는다(int day) {
        assertThatCode(() -> AttendanceChecker.validateCampusDay(day)).doesNotThrowAnyException();
    }

}
