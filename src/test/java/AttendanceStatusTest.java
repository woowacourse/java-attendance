import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceStatus;
import domain.AttendanceTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceStatusTest {

    @DisplayName("시작 시간으로부터 5분 초과는 지각으로 간주한다")
    @Test
    void isLateByTime() {
        // given
        AttendanceTime attendanceTime = new AttendanceTime(10, 10);
        int dayStartHour = 10;
        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findByStartHourAndAttendanceTime(dayStartHour, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("시작 시간으로부터 30분 초과는 결석으로 간주한다")
    @Test
    void isAbsenceByTime() {
        // given
        AttendanceTime attendanceTime = new AttendanceTime(10, 40);
        int dayStartHour = 10;
        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findByStartHourAndAttendanceTime(dayStartHour, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @DisplayName("시작시간이며 5분이하 혹은 시작시간보다 출석시간이 작다면 출석으로 간주한다")
    @ParameterizedTest
    @CsvSource({
            "10, 3",
            "9, 40"
    })
    void isAttendanceByTime(int hour, int minute) {
        // given
        AttendanceTime attendanceTime = new AttendanceTime(hour, minute);
        int dayStartHour = 10;
        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.findByStartHourAndAttendanceTime(dayStartHour, attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }
}
