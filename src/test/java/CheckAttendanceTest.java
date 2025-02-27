import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;


public class CheckAttendanceTest {

    // 1. 시간을 주면 상태를 반환한다.

    @Test
    public void checkAttendanceStatusTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        AttendanceStatus attendanceStatus = AttendancePolicy.checkAttendanceStatus(attendanceTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
    }
}
