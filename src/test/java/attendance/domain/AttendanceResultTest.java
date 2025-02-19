package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceResultTest {
    @Test
    void create() {
        AttendanceResult result = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                "출석"
        );

        assertThat(result).isNotNull();
        assertThat(result.getAttendanceTime()).isEqualTo(LocalDateTime.of(2024, 12, 26, 10, 00));
        assertThat(result.getAttendanceType()).isEqualTo("출석");
    }

    @Test
    void modify_attendance_result() {
        //given
        AttendanceResult attendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 11, 00),
                "결석"
        );
        AttendanceResult modifyAttendanceResult = new AttendanceResult(
                LocalDateTime.of(2024, 12, 26, 10, 00),
                "출석"
        );

        //when
        attendanceResult.modify(modifyAttendanceResult);

        //then
        assertThat(attendanceResult).isEqualTo(modifyAttendanceResult);
        assertThat(attendanceResult.getAttendanceTime()).isEqualTo(modifyAttendanceResult.getAttendanceTime());
        assertThat(attendanceResult.getAttendanceType()).isEqualTo("출석");
    }
}
