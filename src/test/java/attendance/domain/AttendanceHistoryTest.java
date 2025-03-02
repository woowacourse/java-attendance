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

    @Test
    void 입력_받은_날짜에_대한_출석_기록을_가져온다() {

        // given
        final AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.add(new AttendanceTime(LocalDate.of(2025, 2, 27), 10, 20));

        // when
        final AttendanceTime attendanceTime = attendanceHistory.getAttendanceTime(LocalDate.of(2025, 2, 27));

        // then
        org.junit.jupiter.api.Assertions.assertAll(() -> {
            org.junit.jupiter.api.Assertions.assertEquals(attendanceTime.getDate(), LocalDate.of(2025, 2, 27));
            org.junit.jupiter.api.Assertions.assertEquals(attendanceTime.getHour(), 10);
            org.junit.jupiter.api.Assertions.assertEquals(attendanceTime.getMinute(), 20);
        });
    }
}
