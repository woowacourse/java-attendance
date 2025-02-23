package attendance.domain.constant;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class AttendanceStatusTest {

    @Test
    void 주말_반환_테스트() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 23, 9, 59);
        //when
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateStatus(localDateTime);
        //then
        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.HOLIDAY);
    }

}