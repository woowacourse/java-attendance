package attendance.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("출석부 테스트")
public class AttendanceBookTest {

    @Test
    void 출석부에_크루별_출석을_추가할수_있다() {
        AttendanceBook attendanceBook = new AttendanceBook(new HashMap<>());
        Crew crew = new Crew(new Nickname("듀이"));
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 0));
        attendanceBook.add(crew, attendance);

        assertThat(attendanceBook.size()).isEqualTo(1);
    }
}
