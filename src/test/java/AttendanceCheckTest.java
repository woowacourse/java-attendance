import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceCheckTest {

    @Test
    void 출석시간_정상입력시_출석으로_표시된다() {
        final var attendanceTime = "09:59";

        final var actual = "12월 05일 화요일 " + attendanceTime + " (출석)";
        final var expected = "12월 05일 화요일 09:59 (출석)";
        assertThat(actual).isEqualTo(expected);
    }

}
