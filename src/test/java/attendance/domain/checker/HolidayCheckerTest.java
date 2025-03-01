package attendance.domain.checker;

import static attendance.fixture.DateFixture.NOT_HOLIDAY;
import static attendance.fixture.DateFixture.PUBLIC_HOLIDAY;
import static attendance.fixture.DateFixture.SATURDAY;
import static attendance.fixture.DateFixture.SUNDAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class HolidayCheckerTest {

    HolidayChecker holidayChecker = new HolidayChecker();

    @BeforeEach
    void beforeEach() {
        holidayChecker.addPublicHoliday(PUBLIC_HOLIDAY);
    }


    @DisplayName("공휴일을 추가할 수 있다")
    @Test
    void 휴일을_추가할_수_있다() {
        LocalDate newHoliday = LocalDate.of(2025, 2, 4);
        holidayChecker.addPublicHoliday(newHoliday);

        assertThat(holidayChecker.checkHoliday(newHoliday))
                .isTrue();
    }

    @DisplayName("휴일인지 판별할 수 있다.")
    @Test
    void 휴일인지_판별할_수_있다() {
        assertThat(holidayChecker.checkHoliday(SATURDAY)).isTrue();
        assertThat(holidayChecker.checkHoliday(SUNDAY)).isTrue();
        assertThat(holidayChecker.checkHoliday(PUBLIC_HOLIDAY)).isTrue();
        assertThat(holidayChecker.checkHoliday(NOT_HOLIDAY)).isFalse();
    }


    @DisplayName("휴일이 아닌 경우를 검증할 수 있다")
    @ParameterizedTest
    @MethodSource()
    void 휴일이_아닌_경우를_검증할_수_있다(LocalDate holiday) {
        assertThatThrownBy(() -> holidayChecker.validateNotHoliday(holiday))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(makeHolidayAttendanceExceptionMessage(holiday));
    }

    static Stream<Arguments> 휴일이_아닌_경우를_검증할_수_있다() {
        return Stream.of(
                Arguments.of(SATURDAY),
                Arguments.of(SUNDAY),
                Arguments.of(PUBLIC_HOLIDAY)
        );
    }

    String makeHolidayAttendanceExceptionMessage(LocalDate date) {
        return String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                date.getMonth().getValue(), date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));
    }
}