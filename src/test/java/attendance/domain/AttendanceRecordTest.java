package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceRecordTest {

    @DisplayName("출석한 시간에 기반하여 출석 상태를 알맞게 결정한다 - 출석")
    @Test
    void test() {
        // given
        LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 2, 13, 0);

        // when
        AttendanceRecord record = new AttendanceRecord(attendanceDateTime);

        // then
        AttendanceStatus status = record.getAttendanceStatus();
        Assertions.assertThat(status).isEqualTo(AttendanceStatus.PRESENT);
    }

}