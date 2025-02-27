package attendance.domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AttendanceRecordTest {

    @Test
    public void 출석_기록_추가() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9, 59);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(localDateTime);

        Assertions.assertThat(attendanceRecord.getAttendanceRecord().size()).isEqualTo(1);
    }

    @Test
    public void 패널티_상태_반환() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        LocalDateTime attendanceTimeTwo = LocalDateTime.of(2025, 2, 25, 10, 31);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(attendanceTimeOne);
        attendanceRecord.addAttendanceTime(attendanceTimeTwo);

        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.WARNING);
    }
}
