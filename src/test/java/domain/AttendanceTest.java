package domain;

import attendance.domain.AttendanceType;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 해당_요일의_시작_시각으로부터_5분_초과는_지각이다() {
        //given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime attendanceTime = LocalTime.of(13, 6);

        //when
        AttendanceType result = AttendanceType.calculate(dayOfWeek, attendanceTime);

        //then
        Assertions.assertThat(result).isEqualTo(AttendanceType.LATE);
    }
}
