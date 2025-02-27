import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatusTest {
    @DisplayName("출석 시간이 교육 시작 시간으로부터 5분 이내일 경우 출석을 반환한다.")
    @Test
    void presentReturnTest() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, 5);

        // then
        assertThat(AttendanceStatus.getStatus(dayOfWeek, time)).isEqualTo(AttendanceStatus.PRESENT);
    }

    @DisplayName("출석 시간이 교육 시작 시간으로부터 5분 초과 30분 이내일 경우 지각을 반환한다.")
    @Test
    void tardyReturnTest() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, 10);

        // then
        assertThat(AttendanceStatus.getStatus(dayOfWeek, time)).isEqualTo(AttendanceStatus.TARDY);
    }

    @DisplayName("출석 시간이 교육 시작 시간으로부터 30분 초과할 경우 결석을 반환한다.")
    @Test
    void absentReturnTest() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, 35);

        // then
        assertThat(AttendanceStatus.getStatus(dayOfWeek, time)).isEqualTo(AttendanceStatus.ABSENT);
    }
}
