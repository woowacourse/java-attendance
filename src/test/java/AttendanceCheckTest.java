import domain.Attendance;
import domain.AttendanceDto;
import domain.Day;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import util.Converter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AttendanceCheckTest {

    public static Stream<Arguments> getDayOfWeekAndAttendanceTime() {
        return Stream.of(
                // 월요일
                Arguments.of(LocalDate.of(2025, 2, 17), LocalTime.of(13, 4), false, false),
                Arguments.of(LocalDate.of(2025, 2, 17), LocalTime.of(13, 6), true, false),
                Arguments.of(LocalDate.of(2025, 2, 17), LocalTime.of(13, 31), false, true),
                Arguments.of(LocalDate.of(2025, 2, 20), LocalTime.of(10, 4), false, false),
                // 목요일
                Arguments.of(LocalDate.of(2025, 2, 20), LocalTime.of(10, 6), true, false),
                Arguments.of(LocalDate.of(2025, 2, 20), LocalTime.of(13, 4), false, true)
        );
    }

    @Test
    void 출석시간_정상입력시_출석으로_표시된다() {
        final var attendanceTime = "09:59";

        final var actual = "12월 05일 화요일 " + attendanceTime + " (출석)";
        final var expected = "12월 05일 화요일 09:59 (출석)";
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10:61"})
    void 존재하지않는_시간을_입력하면_예외가_발생한다(String value) {
        assertThatThrownBy(() -> Converter.convertStringToLocalTime(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"테스트:00"})
    void 시간형식이_아닌값을_입력하면_예외가_발생한다(String value) {
        assertThatThrownBy(() -> Converter.convertStringToLocalTime(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @MethodSource("getDayOfWeekAndAttendanceTime")
    void 날짜에_따른_출석_기준_시간을_적용한다(LocalDate date, LocalTime attendanceTime, Boolean isLate, Boolean isAbsent) {
        Attendance attendance = new Attendance(new Day(date), attendanceTime);
        AttendanceDto dto = attendance.toDto();

        Boolean actualIsLate = dto.getLate();
        Boolean actualIsAbsent = dto.getAbsent();

        assertThat(actualIsLate).isEqualTo(isLate);
        assertThat(actualIsAbsent).isEqualTo(isAbsent);
    }

    @Test
    void 주말에_출석을_시도하면_예외가_발생한다() {
        final var today = LocalDate.of(2025, 2, 22);

        assertThatThrownBy(() -> new Attendance(new Day(today), LocalTime.of(10, 31)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
