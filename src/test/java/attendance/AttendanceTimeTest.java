package attendance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @Test
    void _08시_이전에는_출석할_수_없다() {
        LocalTime time = LocalTime.of(7, 0);
        assertThatThrownBy(() -> AttendanceTime.from(time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _23시_이후에는_출석할_수_없다() {
        LocalTime time = LocalTime.of(23, 30);
        assertThatThrownBy(() -> AttendanceTime.from(time))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void _캠퍼스_운영시간_내에는_출석_가능하다() {
        LocalTime time = LocalTime.of(9, 0);
        assertThatCode(() -> AttendanceTime.from(time))
                .doesNotThrowAnyException();
    }

    @Test
    void 월요일에_13시_5분_초과는_지각이다() {
        LocalTime time = LocalTime.of(13, 6);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        assertThat(attendanceTime.checkAttendanceStatus(true)).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 월요일에_13시_30분_초과는_결석이다() {
        LocalTime time = LocalTime.of(13, 31);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        assertThat(attendanceTime.checkAttendanceStatus(true)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일에_13시_5분_이전이면_정상_출석이다() {
        LocalTime time = LocalTime.of(13, 0);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        assertThat(attendanceTime.checkAttendanceStatus(true)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 다른_요일에_10시_5분_초과는_지각이다() {
        LocalTime time = LocalTime.of(10, 6);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        assertThat(attendanceTime.checkAttendanceStatus(false)).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 다른_요일에_10시_30분_초과는_결석이다() {
        LocalTime time = LocalTime.of(10, 31);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        assertThat(attendanceTime.checkAttendanceStatus(false)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 다른_요일에_10시_5분_이전이면_정상_출석이다() {
        LocalTime time = LocalTime.of(10, 0);
        AttendanceTime attendanceTime = AttendanceTime.from(time);

        assertThat(attendanceTime.checkAttendanceStatus(false)).isEqualTo(AttendanceStatus.ATTEND);
    }
}
