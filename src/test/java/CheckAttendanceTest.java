import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class CheckAttendanceTest {

    // 1. 시간을 주면 상태를 반환한다.

    @Test
    public void checkAttendanceStatusTest() {
        LocalDateTime time1 = LocalDateTime.of(2024, 12, 17, 10, 0);
        AttendanceStatus attendanceStatus = AttendancePolicy.checkAttendanceStatusTest(time1);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
    }
}
