package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("휴일 테스트")
class HolidaysTest {

    @Test
    @DisplayName("휴일에 새로운 공휴일을 추가한다")
    void addNewHolidayToHolidays() {
        // given
        LocalDate addHoliday = LocalDate.of(2024, 12, 25);
        Holidays holidays = new Holidays();

        // when
        holidays.addHoliday(addHoliday);

        // then
        assertThat(holidays.contains(addHoliday)).isTrue();
    }

    @Test
    @DisplayName("평일에 출석할 경우 예외가 발생하지 않는다")
    void 등교_일자인_경우_예외가_발생하지_않는다() {
        // given
        Holidays holidays = new Holidays();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThatNoException()
                .isThrownBy(() -> holidays.validateAttendanceDate(attendanceDate));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-01",
            "2024-12-07",
            "2024-12-14"
    })
    @DisplayName("주말에 출석할 경우 예외가 발생한다")
    void shouldThrowExceptionWhenNotASchoolDay(LocalDate attendanceDate) {
        // given
        Holidays holidays = new Holidays();

        // that
        assertThatIllegalArgumentException()
                .isThrownBy(() -> holidays.validateAttendanceDate(attendanceDate))
                .withMessage(formatErrorMessage(attendanceDate));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-25",
            "2025-01-01",
            "2025-05-05"
    })
    @DisplayName("공휴일에 출석할 경우 예외가 발생한다")
    void 공휴일에_출석한_경우_예외가_발생한다(LocalDate attendanceDate) {
        // given
        Holidays holidays = new Holidays();
        holidays.addHoliday(attendanceDate);

        // when & then
        assertThatIllegalArgumentException()
                .isThrownBy(() -> holidays.validateAttendanceDate(attendanceDate))
                .withMessage(formatErrorMessage(attendanceDate));
    }

    private static String formatErrorMessage(final LocalDate date) {
        return String.format("[ERROR] %d월 %d일 %s은 등교일이 아닙니다.",
                date.getMonthValue(),
                date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA)
        );
    }
}
