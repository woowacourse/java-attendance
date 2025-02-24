import domain.AttendanceStatus;
import domain.CrewAttendances;
import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendancesTest {

    @Nested
    class AddAttendance {
        @Test
        @DisplayName("닉네임과 등교 시간을 입력해 출석할 수 있다")
        void addAttendance() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            LocalDate date = LocalDate.of(2024, 12, 3);
            CrewAttendances crewAttendances = new CrewAttendances();
            crewAttendances.addAttendance(nickname, time);
            System.out.println();

            Assertions.assertThat(
                    crewAttendances.crewAttendance(nickname, date).attendanceStatus()
            ).isEqualTo(AttendanceStatus.ATTENDANCE);
        }
    }
}
