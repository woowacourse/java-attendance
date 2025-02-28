package domain;

import attendance.domain.AttendanceStatus;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    @Test
    void 해당_요일의_시작_시각으로부터_5분_초과는_지각이다() {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime attendanceTime = LocalTime.of(13, 6);

        //when
        AttendanceStatus result = AttendanceStatus.calculate(dayOfWeek, attendanceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 해당_요일의_시작_시간으로부터_30분_초과는_결석이다() {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime attendanceTime = LocalTime.of(13, 31);

        //when
        AttendanceStatus result = AttendanceStatus.calculate(dayOfWeek, attendanceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendanceStatus.ABSENT);
    }

    @Test
    void 해당_요일의_시작_시각으로부터_5분을_초과하지_않는다면_출석이다() {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime attendanceTime = LocalTime.of(13, 5);

        //when
        AttendanceStatus result = AttendanceStatus.calculate(dayOfWeek, attendanceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendanceStatus.ATTENDANCE);
    }
}
