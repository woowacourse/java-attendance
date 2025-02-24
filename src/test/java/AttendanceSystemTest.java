import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class AttendanceSystemTest {
    private final AttendanceSystem attendanceSystem = new AttendanceSystem();
    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다")
    @Test
    void attendance_with_name() {
        String name = "두리";
        LocalTime time = LocalTime.of(10, 0);
        attendanceSystem.attendance(name, time);
        Assertions.assertThat(attendanceSystem.getAttendanceRecord(name, attendanceSystem.TODAY)).isEqualTo(LocalDateTime.of(attendanceSystem.TODAY, time));
    }

    @DisplayName("이름과 등교시간을 입력하면 오늘 날짜로 출석할 수 있다2")
    @Test
    void attendance_with_name2() {
        String name = "두리";
        LocalTime time = LocalTime.of(10, 30);
        attendanceSystem.attendance(name, time);
        Assertions.assertThat(attendanceSystem.getAttendanceRecord(name, attendanceSystem.TODAY)).isEqualTo(LocalDateTime.of(attendanceSystem.TODAY, time));
    }

    @DisplayName("이미 출석한 경우 다시 출석할 수 없다")
    @Test
    void cannot_attendance_if_already_attend() {
        String name = "두리";
        LocalTime time = LocalTime.of(10, 0);
        attendanceSystem.attendance(name, time);
        Assertions.assertThatThrownBy(() -> {
            attendanceSystem.attendance(name, time);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
