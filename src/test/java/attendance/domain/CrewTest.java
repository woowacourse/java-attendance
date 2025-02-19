package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @Test
    void create_crew() {
        Crew crew = new Crew("젠슨");
        assertThat(crew.getName()).isEqualTo("젠슨");
    }

    @Test
    void add_attendance_history() {
        Crew crew = new Crew("젠슨");
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 26, 10, 00);
        String attendanceResult = "출석";
        AttendanceResult attendanceHistory = new AttendanceResult(localDateTime, attendanceResult);
        crew.addAttendanceHistory(attendanceHistory);
        assertThat(crew.getAttendanceHistory()).isNotNull();
        assertThat(crew.getAttendanceHistory().size()).isEqualTo(1);
        assertThat(crew.getAttendanceHistory()).contains(attendanceHistory);
    }
}

