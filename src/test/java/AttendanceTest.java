import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 출석부를_생성할_수_있다() {
        //given
        Crew crew = new Crew("도기");
        List<LocalDateTime> attendanceRecord = List.of(
                LocalDateTime.of(2024, 12, 13, 10, 10),
                LocalDateTime.of(2024, 12, 13, 10, 7)
        );

        //when
        Attendance attendance = new Attendance(crew, attendanceRecord);

        //then
        assertThat(attendance.getCrew().getName()).isEqualTo("도기");
        assertThat(attendance.getDateTimes()).containsExactly(
                LocalDateTime.of(2024, 12, 13, 10, 10),
                LocalDateTime.of(2024, 12, 13, 10, 7)
        );
    }
}
