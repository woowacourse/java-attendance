package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AttendanceRecordTest {

    @Test
    public void 출석_기록_추가() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9, 59);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(localDateTime);

        Assertions.assertThat(attendanceRecord.getAttendanceRecord().getFirst()).isEqualTo(localDateTime);
    }
}
