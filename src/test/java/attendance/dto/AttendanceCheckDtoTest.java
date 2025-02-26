package attendance.dto;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.domain.Attendance;
import attendance.domain.AttendanceType;
import attendance.domain.Crew;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCheckDtoTest {
    private Attendance attendance;

    @BeforeEach
    void setUp() {
        attendance = new Attendance(
                new Crew("리원"),
                LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                AttendanceType.SAFE
        );
    }

    @DisplayName("기능: 출석 확인 날짜와 시간 및 유형 정보 반환 확인")
    @Test
    void getAttendanceCheckInfo() {
        AttendanceCheckDto attendanceCheckDto = AttendanceCheckDto.fromAttendance(this.attendance);
        assertThat(List.of(attendanceCheckDto.month(), attendanceCheckDto.day(), attendanceCheckDto.dayOfWeek(),
                attendanceCheckDto.attendanceTime(), attendanceCheckDto.attendanceType()))
                .isEqualTo(List.of(2, 19, "수", "09:50", "출석"));
    }
}
