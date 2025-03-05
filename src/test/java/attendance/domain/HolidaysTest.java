package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNoException;

@DisplayName("휴일 테스트")
class HolidaysTest {

    private static final String TEST_HOLIDAYS_FILE = "testHolidays.csv";
    private static final Holidays holidays = new Holidays(TEST_HOLIDAYS_FILE);

    @ParameterizedTest(name = "평일 날짜: {0}")
    @CsvSource({
            "2024-12-02",
            "2024-12-03",
            "2024-12-04",
            "2024-12-05",
            "2024-12-06"
    })
    @DisplayName("평일에 출석할 경우 예외가 발생하지 않는다")
    void shouldNotThrowExceptionWhenAttendingOnWeekday() {
        // given
        LocalDate attendanceDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThatNoException()
                .isThrownBy(() -> holidays.validateAttendanceDate(attendanceDate));
    }

    @ParameterizedTest(name = "주말 날짜: {0}")
    @CsvSource({
            "2024-12-01",
            "2024-12-07",
            "2024-12-14"
    })
    @DisplayName("주말에 출석할 경우 예외가 발생한다")
    void shouldThrowExceptionWhenAttendingOnWeekend(LocalDate attendanceDate) {
        // that
        assertThatIllegalArgumentException()
                .isThrownBy(() -> holidays.validateAttendanceDate(attendanceDate))
                .withMessage(formatErrorMessage(attendanceDate));
    }

    @ParameterizedTest(name = "공휴일 날짜: {0}")
    @CsvSource({
            "2024-12-25",
            "2025-01-01",
            "2025-05-05"
    })
    @DisplayName("공휴일에 출석할 경우 예외가 발생한다")
    void shouldThrowExceptionWhenAttendingOnHoliday(LocalDate attendanceDate) {
        // given
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
