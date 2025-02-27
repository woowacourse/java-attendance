package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

class HolidayTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-01",
            "2024-12-07",
            "2024-12-14"
    })
    @DisplayName("등교 일자가 아닌 경우 예외가 발생한다")
    void shouldThrowExceptionWhenNotASchoolDay(LocalDate attendanceDate) {
        assertThatIllegalArgumentException()
                .isThrownBy(() -> Holiday.validateAttendanceDate(attendanceDate))
                .withMessage(formatErrorMessage(attendanceDate));
    }

    @Test
    @DisplayName("등교 일자인 경우 예외가 발생하지 않는다")
    void 등교_일자인_경우_예외가_발생하지_않는다() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThatNoException()
                .isThrownBy(() -> Holiday.validateAttendanceDate(attendanceDate));
    }

    private static String formatErrorMessage(final LocalDate date) {
        return String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
