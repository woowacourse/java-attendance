package domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    @DisplayName("AttendanceTime 입력 시 출석 상태 반환")
    void convertAttendFromAttendanceTimeTest() {
        // given
        AttendanceTime attendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 2), LocalTime.of(13, 5)
        );

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    @DisplayName("AttendanceTime 입력 시 지각 상태 반환")
    void convertLateFromAttendanceTimeTest() {
        // given
        AttendanceTime attendanceTime1 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)
        );
        AttendanceTime attendanceTime2 = AttendanceTime.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 30)
        );

        // when
        AttendanceStatus attendanceStatus1 = AttendanceStatus.from(attendanceTime1);
        AttendanceStatus attendanceStatus2 = AttendanceStatus.from(attendanceTime2);

        // then
        assertThat(attendanceStatus1).isEqualTo(AttendanceStatus.LATE);
        assertThat(attendanceStatus2).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    @DisplayName("AttendanceTime 입력 시 결석 상태 반환")
    void convertAbsenceFromAttendanceTimeTest() {
        // given
        AttendanceTime attendanceTime = AttendanceTime.of(
                LocalDate.of(2024, 12, 3), LocalTime.of(10, 31)
        );

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.from(attendanceTime);

        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
