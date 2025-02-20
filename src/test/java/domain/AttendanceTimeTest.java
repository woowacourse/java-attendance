package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceTimeTest {

    @Test
    void 출석_시간_수정_확인() {
        LocalDateTime oldDateTime = LocalDateTime.of(2024, 12, 2, 14, 0);
        LocalDateTime newDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        LocalTime newTime = newDateTime.toLocalTime();

        AttendanceTime attendanceTime = new AttendanceTime(oldDateTime);
        attendanceTime.updateAttendanceDateTime(newTime);
        assertThat(attendanceTime.getAttendanceDateTime())
                .isEqualTo(newDateTime);
    }

    @Test
    void 출석_시간_체크_확인() {
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 2, 14, 0);
        AttendanceTime attendanceTime = new AttendanceTime(dateTime);
        assertThat(attendanceTime.checkAttended(dateTime.toLocalDate())).isEqualTo(true);
    }
}
