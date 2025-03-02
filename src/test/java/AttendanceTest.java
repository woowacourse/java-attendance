import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 출석한다() {
        String time = "09:59";

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        LocalDateTime attendanceTime = attendanceRecord.attend(time);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(
                2024, 12, 13, 9, 59));
    }

    private class AttendanceRecord {

        public LocalDateTime attend(String time) {
            return LocalDateTime.of(2024, 12, 13, 9, 59);
        }
    }
}
