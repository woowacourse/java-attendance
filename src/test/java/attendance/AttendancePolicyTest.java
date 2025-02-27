package attendance;

import static attendance.domain.AttendanceStatus.ABSENCE;
import static attendance.domain.AttendanceStatus.ATTENDANCE;
import static attendance.domain.AttendanceStatus.LATE;
import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.AttendancePolicy;
import attendance.domain.AttendanceStatus;
import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendancePolicyTest {
    @DisplayName("출석_시간에_따른_출석_상태_반환")
    @Nested
    class calculateAttendanceStatus {
        @DisplayName("월요일_교육_시작_시간으로부터_5분_이내_출석_시_ATTENDANCE_를_반환한다")
        @Test
        void should_ReturnAttendance_WhenMondayAttendance() {
            //given
            DayOfWeek attendanceDay = DayOfWeek.MONDAY;
            LocalTime attendanceTime = LocalTime.of(13, 05);

            //when
            AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(attendanceDay, attendanceTime);

            //then
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @DisplayName("월요일_출석_시간이_교육_시작_시간으로부터_5분_초과_시_LATE_를_반환한다")
        @Test
        void should_ReturnLate_WhenMondayLate() {
            //given
            DayOfWeek attendanceDay = DayOfWeek.MONDAY;
            LocalTime attendanceTime = LocalTime.of(13, 06);

            //when
            AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(attendanceDay, attendanceTime);

            //then
            assertThat(result).isEqualTo(LATE);
        }

        @DisplayName("월요일_출석_시간이_교육_시작_시간으로부터_30분_초과_시_ABSENCE_를_반환한다")
        @Test
        void should_ReturnAbsence_WhenMondayAbsence() {
            //given
            DayOfWeek attendanceDay = DayOfWeek.MONDAY;
            LocalTime attendanceTime = LocalTime.of(13, 31);

            //when
            AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(attendanceDay, attendanceTime);

            //then
            assertThat(result).isEqualTo(ABSENCE);
        }

        @DisplayName("월요일_아닌_출석_시간이_교육_시작_시간으로부터_5분_이내_출석_시_ATTENDANCE_를_반환한다")
        @Test
        void should_ReturnAttendance_WhenNotMondayAttendance() {
            //given
            DayOfWeek attendanceDay = DayOfWeek.WEDNESDAY;
            LocalTime attendanceTime = LocalTime.of(10, 05);

            //when
            AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(attendanceDay, attendanceTime);

            //then
            assertThat(result).isEqualTo(ATTENDANCE);
        }

        @DisplayName("월요일_아닌_출석_시간이_교육_시작_시간으로부터_5분_초과_시_LATE_를_반환한다")
        @Test
        void should_ReturnLate_WhenNotMondayLate() {
            //given
            DayOfWeek attendanceDay = DayOfWeek.WEDNESDAY;
            LocalTime attendanceTime = LocalTime.of(10, 06);

            //when
            AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(attendanceDay, attendanceTime);

            //then
            assertThat(result).isEqualTo(LATE);
        }

        @DisplayName("월요일_아닌_출석_시간이_교육_시작_시간으로부터_30분_초과_시_ABSENCE_를_반환한다")
        @Test
        void should_ReturnAbsence_WhenNotMondayAbsence() {
            //given
            DayOfWeek attendanceDay = DayOfWeek.WEDNESDAY;
            LocalTime attendanceTime = LocalTime.of(10, 31);

            //when
            AttendanceStatus result = AttendancePolicy.calculateAttendanceStatus(attendanceDay, attendanceTime);

            //then
            assertThat(result).isEqualTo(ABSENCE);
        }
    }
}
