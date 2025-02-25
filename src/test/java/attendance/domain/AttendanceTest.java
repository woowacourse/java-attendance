package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayName("출석 정보")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class AttendanceTest {

    @Test
    void 출석_시간으로_출석_정보를_생성한다() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 13, 10, 4);
        Attendance attendance = new Attendance(attendanceTime);

        assertThat(attendance.getHour()).isEqualTo(10);
        assertThat(attendance.getMinute()).isEqualTo(4);
    }

    @Test
    void 출석_정보_생성_시_출석_상태를_저장한다() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 13, 10, 4);
        Attendance attendance = new Attendance(attendanceTime);

        assertThat(attendance.attendanceStatus()).isEqualTo(AttendanceStatus.PRESENT);
    }

}
