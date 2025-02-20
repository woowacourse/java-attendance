package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    @Test
    void 출석_상태_확인() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 5);
        AttendanceStatus attendanceStatus = AttendanceStatus.findStatus(attendanceTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 지각_상태_확인() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 5, 1);
        AttendanceStatus attendanceStatus = AttendanceStatus.findStatus(attendanceTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 결석_상태_확인() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 3, 10, 30, 1);
        AttendanceStatus attendanceStatus = AttendanceStatus.findStatus(attendanceTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENT);
    }
}
