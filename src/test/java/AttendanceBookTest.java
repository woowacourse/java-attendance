import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import attendance.model.AttendanceTime;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @Test
    void 입력_받은_이름과_날짜로_출석_기록을_추가한다() {

        // given
        final AttendanceTime attendanceTime = new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 5);

        // when
        final AttendanceBook attendanceBook = new AttendanceBook();
        attendanceBook.add("이름", attendanceTime);

        // then
        final AttendanceTime result = attendanceBook.getAttendance("이름", LocalDate.of(2025, 2, 27));
        assertThat(result).isEqualTo(attendanceTime);
    }

}
