package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.AttendanceDate;
import attendance.model.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTimeTest {

    @Test
    @DisplayName("출석_시작_시간으로_부터_현재_시간이_몇분_차이인지_계산한다(빨리온경우)")
    void 출석_시작_시간으로_부터_현재_시간이_몇분_차이인지_계산한다1() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(9, 58));
        assertThat(attendanceTime.computeMinuteDelta(LocalTime.of(10, 5))).isEqualTo(-7);
    }

    @Test
    @DisplayName("출석_시작_시간으로_부터_현재_시간이_몇분_차이인지_계산한다(늦게온경우)")
    void 출석_시작_시간으로_부터_현재_시간이_몇분_차이인지_계산한다2() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 10));
        assertThat(attendanceTime.computeMinuteDelta(LocalTime.of(10, 5))).isEqualTo(5);
    }

    @Test
    @DisplayName("출석_시작_시간으로_부터_현재_시간이_몇분_차이인지_계산한다(딱 맞춰 온 경우)")
    void 출석_시작_시간으로_부터_현재_시간이_몇분_차이인지_계산한다3() {
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 5));
        assertThat(attendanceTime.computeMinuteDelta(LocalTime.of(10, 5))).isZero();
    }
}
