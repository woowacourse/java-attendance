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

public class AttendanceModifyDtoTest {
    private Attendance newAttendance;

    @BeforeEach
    void setUp() {
        newAttendance = new Attendance(
                new Crew("엠제이"),
                LocalDateTime.of(2025, 2, 19, 9, 50, 0),
                AttendanceType.SAFE
        );
    }

    @DisplayName("기능: 출석 수정 날짜와 시간 및 유형 정보 반환 확인")
    @Test
    void getAttendanceModifyInfo() {
        String originalTime = "10:10";
        AttendanceType originalType = AttendanceType.LATE;

        AttendanceModifyDto attendanceModifiedDto = AttendanceModifyDto.fromModifiedAttendance(originalTime,
                originalType, this.newAttendance);
        assertThat(
                List.of(attendanceModifiedDto.originalTime(), attendanceModifiedDto.originalType(),
                        attendanceModifiedDto.month(), attendanceModifiedDto.day(), attendanceModifiedDto.dayOfWeek(),
                        attendanceModifiedDto.newAttendanceTime(), attendanceModifiedDto.newAttendanceType()))
                .isEqualTo(List.of("10:10", "지각", 2, 19, "수", "09:50", "출석"));
    }
}
