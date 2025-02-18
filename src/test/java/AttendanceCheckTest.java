import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class AttendanceCheckTest {

    @Test
    void 출석시간_정상입력시_출석으로_표시된다() {
        final var attendanceTime = "09:59";

        final var actual = "12월 05일 화요일 " + attendanceTime + " (출석)";
        final var expected = "12월 05일 화요일 09:59 (출석)";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 출석시간이_지각범위내라면_지각으로_표시된다() {
        LocalTime standardTime = LocalTime.of(10, 0);
        LocalTime attendanceTime = LocalTime.of(10, 6);

        final var attendanceCheck = AttendanceCheck.checkAttendanceStatus(standardTime, attendanceTime);

        final var actual = "12월 05일 화요일 " + attendanceTime + " (" + attendanceCheck + ")";
        final var expected = "12월 05일 화요일 10:06 (지각)";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 출석시간이_결석범위라면_결석으로_표시된다() {
        LocalTime standardTime = LocalTime.of(10, 0);
        LocalTime attendanceTime = LocalTime.of(10, 31);

        final var attendanceCheck = AttendanceCheck.checkAttendanceStatus(standardTime, attendanceTime);

        final var actual = "12월 05일 화요일 " + attendanceTime + " (" + attendanceCheck + ")";
        final var expected = "12월 05일 화요일 10:31 (결석)";
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 출석확인시_현재날짜가_출력되어야한다() {
        LocalTime standardTime = LocalTime.of(10, 0);
        LocalTime attendanceTime = LocalTime.of(10, 31);

        final var attendanceCheck = AttendanceCheck.checkAttendanceStatus(standardTime, attendanceTime);
        final var today = LocalDate.now();
        final var month = today.getMonth().getValue();
        final var date = today.getDayOfMonth();
        final var dayOfWeek = AttendanceCheck.convertKorean(today);

        final var actual = month + "월 " + date + "일 " + dayOfWeek + " " + attendanceTime + " (" + attendanceCheck + ")";
        final var expected = "2월 18일 화요일 10:31 (결석)";
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10:31"})
    void 입력받은_문자열이_LocalTime_객체로_정상변환된다(String value) {
        LocalTime standardTime = LocalTime.of(10, 0);

        LocalTime attendanceTime = AttendanceCheck.convertToLocalTime(value);

        final var attendanceCheck = AttendanceCheck.checkAttendanceStatus(standardTime, attendanceTime);
        final var today = LocalDate.now();
        final var month = today.getMonth().getValue();
        final var date = today.getDayOfMonth();
        final var dayOfWeek = AttendanceCheck.convertKorean(today);

        final var actual = month + "월 " + date + "일 " + dayOfWeek + " " + attendanceTime + " (" + attendanceCheck + ")";
        final var expected = "2월 18일 화요일 10:31 (결석)";
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(strings = {"10:61"})
    void 존재하지않는_시간을_입력하면_예외가_발생한다(String value) {
        assertThatThrownBy(() -> AttendanceCheck.convertToLocalTime(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

    @ParameterizedTest
    @ValueSource(strings = {"테스트:00"})
    void 시간형식이_아닌값을_입력하면_예외가_발생한다(String value) {
        assertThatThrownBy(() -> AttendanceCheck.convertToLocalTime(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageStartingWith("[ERROR]");
    }

}
