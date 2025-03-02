import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 출석한다() {
        String time = "09:59";

        AttendanceRecord attendanceRecord = new AttendanceRecord();
        LocalDateTime attendanceTime = attendanceRecord.attend(time);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(
                Today.TODAY,
                LocalTime.of(
                        Integer.parseInt(time.split(":")[0]),
                        Integer.parseInt(time.split(":")[1])
                )));
    }

    class AttendanceRecord {

        private List<LocalDateTime> attendanceTimes;

        public LocalDateTime attend(String time) {
            return LocalDateTime.of(
                    Today.TODAY,
                    LocalTime.of(
                            Integer.parseInt(time.split(":")[0]),
                            Integer.parseInt(time.split(":")[1])
                    ));
        }
    }
}
