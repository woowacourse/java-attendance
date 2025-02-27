import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {

    @DisplayName("크루의 출석 정보를 저장할 수 있다.")
    @Test
    void add() {
        //given
        Attendance attendance = createAttendance();
        LocalDateTime addTime = LocalDateTime.of(2024, 12, 12, 10, 0);

        //when
        attendance.add(addTime);

        //then
        assertThat(attendance.getAttendanceTime()).hasSize(3);
    }

    private Attendance createAttendance() {
        Crew crew = Crew.of("도기");

        List<LocalDateTime> attendanceTime = new ArrayList<>();
        LocalDateTime localDateTime = LocalDateTime.of(2024, 12, 1, 10, 10);
        LocalDateTime localDateTime1 = LocalDateTime.of(2024, 12, 2, 11, 11);

        attendanceTime.add(localDateTime);
        attendanceTime.add(localDateTime1);

        return new Attendance(crew, attendanceTime);
    }
}
