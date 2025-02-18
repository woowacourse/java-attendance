package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {
    @Test
    void create() {
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 26, 10, 00);
        String attendanceResult = "출석";
        AttendanceHistory result = new AttendanceHistory(localDateTime, attendanceResult);

        assertThat(result).isNotNull();
        assertThat(result.getAttendanceTime()).isEqualTo(localDateTime);
        assertThat(result.getAttendanceResult()).isEqualTo(attendanceResult);
    }
}
