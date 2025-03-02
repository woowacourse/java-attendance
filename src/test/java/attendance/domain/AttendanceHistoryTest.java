package attendance.domain;

import java.time.LocalDate;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @Test
    void 입력_받은_시간으로_출석_기록을_추가한다() {

        // given®
        final AttendanceHistory attendanceHistory = new AttendanceHistory();

        // when
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 10));

        // then
        Assertions.assertThat(attendanceHistory.getHistory().size()).isEqualTo(1);
    }
}
