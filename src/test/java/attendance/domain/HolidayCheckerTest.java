package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class HolidayCheckerTest {

    final LocalDate SATURDAY = LocalDate.of(2024, 12, 7);
    final LocalDate SUNDAY = LocalDate.of(2024, 12, 7);
    final LocalDate CHRISTMAS_IN_WEDNESDAY = LocalDate.of(2024, 12, 25);
    final LocalDate NOT_HOIlDAY = LocalDate.of(2024, 12, 12);
    HolidayChecker holidayChecker;

    @BeforeEach
    void beforeEach() {
        holidayChecker = new HolidayChecker();
        holidayChecker.addHoliday(CHRISTMAS_IN_WEDNESDAY);
    }

    @DisplayName("공휴일을 추가할 수 있다.")
    @Test
    void 공휴일을_추가할_수_있다() {
        LocalDate newPublicHoliday = LocalDate.of(2024, 12, 24);

        holidayChecker.addHoliday(newPublicHoliday);

        boolean isHoliday = holidayChecker.isHoliday(newPublicHoliday);
        assertThat(isHoliday).isTrue();
    }

    @DisplayName("주말인 경우 휴일로 간주한다.")
    @ParameterizedTest
    @CsvSource({"2024-12-07", "2024-12-08"})
    void 주말인_경우_휴일로_간주한다() {
        assertThat(holidayChecker.isHoliday(SATURDAY)).isTrue();
        assertThat(holidayChecker.isHoliday(SUNDAY)).isTrue();
    }

    @DisplayName("주말이 아닌 공휴일인 경우 휴일로 간주한다.")
    @Test
    void 주말이_아닌_공휴일인_경우_휴일로_간주한다() {
        boolean isHoliday = holidayChecker.isHoliday(CHRISTMAS_IN_WEDNESDAY);
        assertThat(isHoliday).isTrue();
    }

    @DisplayName("주말도 아니고 공휴일도 아닌 경우 휴일로 간주하지 않는다.")
    @Test
    void 주말이_아닌_경우_휴일로_간주하지_않는다() {
        boolean isHoliday = holidayChecker.isHoliday(NOT_HOIlDAY);
        assertThat(isHoliday).isFalse();
    }

    @DisplayName("휴일인 경우를 검증한다.")
    @Test
    void 휴일인_경우를_검증한다() {
        assertThatCode(() -> holidayChecker.validateHoliday(NOT_HOIlDAY))
                .doesNotThrowAnyException();

        String message = String.format(ExceptionMessage.HOLIDAY.getContent(),
                SATURDAY.getMonth().getValue(), SATURDAY.getDayOfMonth(),
                SATURDAY.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.KOREA));
        assertThatIllegalArgumentException()
                .isThrownBy(() -> holidayChecker.validateHoliday(SATURDAY))
                .withMessage(message);
    }

    @DisplayName("휴일이 아닌 날의 개수를 계산한다.")
    @Test
    void 휴일이_아닌_날의_개수를_계산한다() {
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);

        int count = holidayChecker.calculateNotHolidayCount(startDate, endDate);
        assertThat(count).isEqualTo(21);
    }
}
