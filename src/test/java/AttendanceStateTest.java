import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStateTest {

    @DisplayName("출석 정보를 받아서 출석 상태를 계산하여 반환한다.")
    @Test
    void findStateBy() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 12, 10, 10);

        //when
        AttendanceState actual = AttendanceState.findStateBy(time);

        //then
        assertThat(actual).isEqualTo(AttendanceState.LATE);
    }

    @DisplayName("날짜가 월요일인 경우 등교시간이 13:00라면 출석이다.")
    @Test
    void attendanceState() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 13, 0);

        //when
        AttendanceState attendanceState = AttendanceState.findStateBy(time);

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.ATTENDANCE);
    }

    @DisplayName("날짜가 월요일인 경우 등교시간이 13:05분을 초과하면 지각이다.")
    @Test
    void attendanceState2() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 13, 6);

        //when
        AttendanceState attendanceState = AttendanceState.findStateBy(time);

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.LATE);
    }

    @DisplayName("날짜가 월요일인 경우 등교시간이 13:30분을 초과하면 지각이다.")
    @Test
    void attendanceState3() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 13, 31);

        //when
        AttendanceState attendanceState = AttendanceState.findStateBy(time);

        //then
        assertThat(attendanceState).isEqualTo(AttendanceState.ABSENCE);
    }

    @DisplayName("날짜가 월요일이 아닐때 등교시간이 10:30을 초과하면 결석이다.")
    @Test
    void attendanceState4() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 10, 31);

        //when
        AttendanceState state = AttendanceState.findStateBy(time);

        //then
        assertThat(state).isEqualTo(AttendanceState.ABSENCE);
    }

    @DisplayName("날짜가 월요일이 아닐때 등교시간이 10:05를 초과하면 지각이다.")
    @Test
    void attendanceState5() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 10, 6);

        //when
        AttendanceState state = AttendanceState.findStateBy(time);

        //then
        assertThat(state).isEqualTo(AttendanceState.LATE);
    }
}
