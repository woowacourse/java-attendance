package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import attendance.domain.Attendance;
import attendance.domain.AttendanceHistory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {
    @Nested
    class findAttendance {
        @DisplayName("주어진_날짜의_출석을_찾아_반환한다")
        @Test
        void should_ReturnAttendance_WhenSameDateExists() {
            //given
            AttendanceHistory attendanceHistory = new AttendanceHistory();
            LocalDate date = LocalDate.of(2024, 12, 26);
            LocalTime time = LocalTime.of(10, 0);
            attendanceHistory.addAttendance(new Attendance(date, time, "LATE"));

            //when
            Optional<Attendance> result = attendanceHistory.findAttendance(date);

            //then
            assertAll(
                    () -> assertThat(result).isNotEmpty(),
                    () -> assertThat(result.get().isDateEquals(date)).isTrue(),
                    () -> assertThat(result.get().getStatus()).isEqualTo("LATE")
            );
        }

        @DisplayName("주어진_날짜의_출석이_없으면_null_을_반환한다")
        @Test
        void should_ReturnEmpty_WhenSameDateNotExists() {
            //given
            AttendanceHistory attendanceHistory = new AttendanceHistory();
            LocalDate date = LocalDate.of(2024, 12, 26);

            //when
            Optional<Attendance> result = attendanceHistory.findAttendance(date);

            //then
            assertThat(result).isEmpty();
        }
    }
}
