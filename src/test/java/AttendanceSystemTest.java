import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceSystemTest {
    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다")
    @Test
    void attendance_with_name() {
        AttendanceSystem attendanceSystem = new AttendanceSystem();
        String name = "두리";
        LocalTime time = LocalTime.of(10, 0);
        attendanceSystem.attendance(name, time);
        Assertions.assertThat(attendanceSystem.getAttendanceRecord(name, attendanceSystem.TODAY)).isEqualTo(LocalDateTime.of(attendanceSystem.TODAY, time));
    }

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다2")
    @Test
    void attendance_with_name2() {
        AttendanceSystem attendanceSystem = new AttendanceSystem();
        String name = "두리";
        LocalTime time = LocalTime.of(10, 30);
        attendanceSystem.attendance(name, time);
        Assertions.assertThat(attendanceSystem.getAttendanceRecord(name, attendanceSystem.TODAY)).isEqualTo(LocalDateTime.of(attendanceSystem.TODAY, time));
    }
}
