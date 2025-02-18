package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {
    @Test
    void create() {
        AttendanceHistory result = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                "출석"
        );

        assertThat(result).isNotNull();
        assertThat(result.getAttendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 26, 10, 00));
        assertThat(result.getAttendanceResult()).isEqualTo("출석");
    }

    @Test
    void modify_attendance_result() {
        AttendanceHistory result = new AttendanceHistory(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                "출석"
        );

        result.modify(
                LocalDateTime.of(2024, 12, 26, 11, 00),
                "결석"
        );
        assertThat(result.getAttendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 26, 11, 00));
        assertThat(result.getAttendanceResult()).isEqualTo("결석");
    }
}
