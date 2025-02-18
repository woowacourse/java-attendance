import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

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

}
