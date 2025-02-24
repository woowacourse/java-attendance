package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("출석 시간을 보고 알맞는 타입을 계산한다.")
    @Test
    void calculateAttendanceType() {
        Attendance attendance = new Attendance(
                new Crew("쿠키"),
                LocalDateTime.parse("2025-02-14 13:03", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
        attendance.calculateAttendanceType();
        assertThat(attendance.getType()).isEqualTo(AttendanceType.ABSENT);
    }
}
