package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AttendanceTimeTest {

    @Test
    public void 출석_시간_생성() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9,59);

        //when
        AttendanceTime attendanceTime = new AttendanceTime(localDateTime);

        //then
        Assertions.assertThat(attendanceTime.getAttendanceTime()).isEqualTo(localDateTime);
    }
}
