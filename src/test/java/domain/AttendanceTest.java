package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @Test
    @DisplayName("요일과 시간을 받아서 출결 정보 값을 반환한다 - 출석")
    void checkPresentStatusTest() {
        // given & when
        Attendance status = Attendance.getAttendanceStatus(Day.MONDAY, LocalTime.of(13, 0));
        // then
        assertThat(status).isEqualTo(Attendance.PRESENT);
    }

    @Test
    @DisplayName("요일과 시간을 받아서 출결 정보 값을 반환한다 - 지각")
    void checkTardyStatusTest() {
        // given & when
        Attendance status = Attendance.getAttendanceStatus(Day.MONDAY, LocalTime.of(13, 6));
        // then
        assertThat(status).isEqualTo(Attendance.TARDY);
    }

    @Test
    @DisplayName("요일과 시간을 받아서 출결 정보 값을 반환한다 - 결석")
    void checkAbsentStatusTest() {
        // given & when
        Attendance status = Attendance.getAttendanceStatus(Day.MONDAY, LocalTime.of(13, 31));
        // then
        assertThat(status).isEqualTo(Attendance.ABSENT);
    }

    @Test
    @DisplayName("교육 시간 이전에 올 경우 출석을 반환한다")
    void checkPresentStatusBeforeStartHour() {
        // given & when
        Attendance status = Attendance.getAttendanceStatus(Day.MONDAY, LocalTime.of(12, 0));
        // then
        assertThat(status).isEqualTo(Attendance.PRESENT);
    }

    @Test
    @DisplayName("운영 시간이 아닌 경우 예외가 발생한다")
    void validateOpenTimeTest() {
        assertThatThrownBy(() -> Attendance.getAttendanceStatus(Day.MONDAY, LocalTime.of(7, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.\n");
    }
}
