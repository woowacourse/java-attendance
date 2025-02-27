package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AttendanceStatusTest {

    @Test
    public void 출석_상태_시간() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9,59);

        //when & then
        Assertions.assertThat(AttendanceStatus.fetchUserAttendanceStatus(localDateTime)).isEqualTo(AttendanceStatus.ATTENDANCE);
    }
}
