package attendance.domain.checker;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.exception.AttendanceException;
import attendance.exception.ExceptionMessage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class AttendanceTypeCheckerTest {

    static final LocalDate SATURDAY = LocalDate.of(2025, 2, 8);
    static final LocalDate SUNDAY = LocalDate.of(2025, 2, 9);
    static final LocalDate PUBLIC_HOLIDAY = LocalDate.of(2025, 2, 24);
    static final LocalDate MONDAY = LocalDate.of(2025, 2, 3);
    static final LocalDate NOT_MONDAY = LocalDate.of(2025, 2, 4);
    static final LocalTime MONDAY_ATTENDANCE_TIME = LocalTime.of(13, 0);
    static final LocalTime MONDAY_LATE_TIME = LocalTime.of(13, 5);
    static final LocalTime MONDAY_ABSENCE_TIME = LocalTime.of(13, 30);
    static final LocalTime NOT_MONDAY_ATTENDANCE_TIME = LocalTime.of(10, 0);
    static final LocalTime NOT_MONDAY_LATE_TIME = LocalTime.of(10, 5);
    static final LocalTime NOT_MONDAY_ABSENCE_TIME = LocalTime.of(10, 30);
    static final LocalTime CAMPUS_START_TIME = LocalTime.of(8, 0);
    static final LocalTime CAMPUS_END_TIME = LocalTime.of(23, 0);

    HolidayChecker holidayChecker = new HolidayChecker();
    AttendanceTypeChecker attendanceTypeChecker = new AttendanceTypeChecker(holidayChecker);

    @BeforeEach
    void beforeEach() {
        holidayChecker.addPublicHoliday(PUBLIC_HOLIDAY);
    }

    @DisplayName("교육시간과 출석정책을 기준으로 월요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다(
            LocalDateTime arrivalDateTime,
            AttendanceType expectedType
    ) {
        AttendanceType actualType = attendanceTypeChecker.check(arrivalDateTime);
        assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 교육시간과_출석정책을_기준으로_월요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ATTENDANCE_TIME), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME.minusSeconds(1)), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_LATE_TIME.plusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME.minusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(MONDAY, MONDAY_ABSENCE_TIME.plusSeconds(1)), AttendanceType.ABSENCE)
        );
    }

    @DisplayName("교육시간과 출석정책을 기준으로 화요일에서 금요일의 출석 상태를 결정한다")
    @ParameterizedTest
    @MethodSource()
    void 교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다(
            LocalDateTime arrivalDateTime,
            AttendanceType expectedType
    ) {
        AttendanceType actualType = attendanceTypeChecker.check(arrivalDateTime);
        assertThat(actualType).isEqualTo(expectedType);
    }

    static Stream<Arguments> 교육시간과_출석정책을_기준으로_화요일에서_금요일의_출석_상태를_결정한다() {
        return Stream.of(
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ATTENDANCE_TIME), AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_LATE_TIME.minusSeconds(1)),
                        AttendanceType.ATTENDANCE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_LATE_TIME), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_LATE_TIME.plusSeconds(1)), AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ABSENCE_TIME.minusSeconds(1)),
                        AttendanceType.LATE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ABSENCE_TIME), AttendanceType.ABSENCE),
                Arguments.of(LocalDateTime.of(NOT_MONDAY, NOT_MONDAY_ABSENCE_TIME.plusSeconds(1)),
                        AttendanceType.ABSENCE)
        );
    }

    @DisplayName("휴일인 경우에는 예외를 발생시킨다")
    @ParameterizedTest
    @MethodSource
    void 휴일인_경우에는_예외를_발생시킨다(LocalDate holiday) {
        LocalDateTime holidayDateTime = LocalDateTime.of(holiday, LocalTime.of(8, 50));

        String expectedMessage = makeHolidayAttendanceExceptionMessage(holiday);
        assertThatThrownBy(() -> attendanceTypeChecker.check(holidayDateTime))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(expectedMessage);
    }

    static Stream<Arguments> 휴일인_경우에는_예외를_발생시킨다() {
        return Stream.of(
                Arguments.of(SATURDAY),
                Arguments.of(SUNDAY),
                Arguments.of(PUBLIC_HOLIDAY)
        );
    }

    @DisplayName("캠퍼스 운영시간이 아닌 경우 예외를 발생시킨다")
    @ParameterizedTest
    @MethodSource
    void 캠퍼스_운영시간이_아닌_경우_예외를_발생시킨다(LocalTime time) {
        LocalDateTime dateTime = LocalDateTime.of(MONDAY, time);

        assertThatThrownBy(() -> attendanceTypeChecker.check(dateTime))
                .isInstanceOf(AttendanceException.class)
                .hasMessage(ExceptionMessage.OUT_OF_CAMPUS_TIME.getMessage());
    }


    static Stream<Arguments> 캠퍼스_운영시간이_아닌_경우_예외를_발생시킨다() {
        return Stream.of(
                Arguments.of(CAMPUS_START_TIME.minusSeconds(1)),
                Arguments.of(CAMPUS_END_TIME),
                Arguments.of(CAMPUS_END_TIME.plusSeconds(1))
        );
    }

    @DisplayName("해당 연도의 월 내에 등교일을 구할 수 있다")
    @Test
    void 해당_연도의_월_내에_등교일을_구할_수_있다() {
        List<LocalDate> notHoliday = attendanceTypeChecker.calculateNotHolidayInMonth(2025, Month.FEBRUARY);
        assertThat(notHoliday)
                .extracting(LocalDate::getDayOfMonth)
                .containsExactly(3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 17, 18, 19, 20, 21, 25, 26, 27, 28);
    }

    String makeHolidayAttendanceExceptionMessage(LocalDate date) {
        return String.format(ExceptionMessage.HOLIDAY_ATTENDANCE.getMessage(),
                date.getMonth().getValue(), date.getDayOfMonth(),
                date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.KOREA));
    }
}