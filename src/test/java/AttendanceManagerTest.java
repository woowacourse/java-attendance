import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceManager;
import domain.AttendanceStatus;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class AttendanceManagerTest {

    @Nested
    class AddAttendance {
        @Test
        @DisplayName("닉네임과 등교 시간을 입력해 출석할 수 있다")
        void addAttendance() {
            String nickname = "투다";
            LocalTime time = LocalTime.of(8, 0);
            AttendanceManager attendanceManager = new AttendanceManager();
            attendanceManager.addAttendance(nickname, time);
            assertThat(
                    attendanceManager.crewAttendanceHistory(nickname, time)
            ).isEqualTo(AttendanceStatus.ATTENDANCE);
        }
    }
}
