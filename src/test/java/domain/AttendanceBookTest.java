import static org.assertj.core.api.Assertions.*;

import domain.Attendance;
import domain.AttendanceBook;
import domain.AttendanceStatus;
import domain.CrewName;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    @DisplayName("닉네임과 등교 시간을 입력하면 출석할 수 있다.")
    @Test
    void test1() {
        CrewName crewName = new CrewName("미미");
        AttendanceBook attendanceBook = new AttendanceBook();

        attendanceBook.addAttendance("미미", LocalDateTime.of(2024, 12, 13, 9, 59));

        assertThat(attendanceBook.getByName("미미")).hasSize(1);
        assertThat(AttendanceStatus.of(savedAttendance)).isEqualTo(AttendanceStatus.ATTEND);
    }
}
