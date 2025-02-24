package domain;

import except.AttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import strategy.TestAttendanceNowDateStrategy;

public class AttendancesTest {

    @Nested
    class AddAttendance {
        @Test
        @DisplayName("닉네임과 등교 시간을 입력해 출석할 수 있다")
        void addAttendance() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            LocalDate date = LocalDate.of(2024, 12, 3);
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceNowDateStrategy(date));
            crewAttendances.addAttendance(nickname, time);

            Assertions.assertThat(
                    crewAttendances.crewAttendance(nickname, date).attendanceStatus()
            ).isEqualTo(AttendanceStatus.ATTENDANCE);
        }

        @Test
        @DisplayName("휴일에 출석시 예외가 발생한다.")
        void addAttendanceAtWeekend() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            LocalDate date = LocalDate.of(2024, 12, 1);
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceNowDateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .hasMessageContaining("휴일에는 출석할 수 없습니다.")
                    .isInstanceOf(AttendanceException.class);
        }

        @Test
        @DisplayName("휴일에 출석시 예외가 발생한다.")
        void addAttendanceNotSchoolRunning() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            LocalDate date = LocalDate.of(2024, 12, 1);
            CrewAttendances crewAttendances = new CrewAttendances(new TestAttendanceNowDateStrategy(date));

            Assertions.assertThatThrownBy(() -> crewAttendances.addAttendance(nickname, time))
                    .hasMessageContaining("휴일에는 출석할 수 없습니다.")
                    .isInstanceOf(AttendanceException.class);
        }
    }
}
