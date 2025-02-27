import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;


public class CheckAttendanceTest {

    // 1. 시간을 주면 상태를 반환한다.

    @Test
    public void checkAttendanceStatusTest() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 17, 10, 0);
        LocalDateTime lateTime = LocalDateTime.of(2024, 12, 17, 10, 30);
        // when
        AttendanceStatus attendanceStatus = AttendancePolicy.checkAttendanceStatus(attendanceTime);
        AttendanceStatus lateStatus = AttendancePolicy.checkAttendanceStatus(attendanceTime);
        // then
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);

    }
}
