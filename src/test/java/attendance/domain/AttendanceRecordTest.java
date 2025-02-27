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
    public void 패널티_없는_상태_반환_결석_1회() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(attendanceTimeOne);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.NONE);
    }

    @Test
    public void 패널티_없는_상태_반환_결석_0회() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 12, 31);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(attendanceTimeOne);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.NONE);
    }

    @Test
    public void 패널티_경고_상태_반환() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        LocalDateTime attendanceTimeTwo = LocalDateTime.of(2025, 2, 25, 10, 31);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(attendanceTimeOne);
        attendanceRecord.addAttendanceTime(attendanceTimeTwo);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.WARNING);
    }

    @Test
    public void 패널티_면담_상태_반환() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        LocalDateTime attendanceTimeTwo = LocalDateTime.of(2025, 2, 25, 10, 31);
        LocalDateTime attendanceTimeThree = LocalDateTime.of(2025, 2, 26, 10, 31);
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(attendanceTimeOne);
        attendanceRecord.addAttendanceTime(attendanceTimeTwo);
        attendanceRecord.addAttendanceTime(attendanceTimeThree);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.COUNSELING);
    }

    @Test
    public void 패널티_제적_상태_반환() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        LocalDateTime attendanceTimeTwo = LocalDateTime.of(2025, 2, 25, 10, 31);
        LocalDateTime attendanceTimeThree = LocalDateTime.of(2025, 2, 26, 13, 31);
        LocalDateTime attendanceTimeFour = LocalDateTime.of(2025, 2, 27, 10, 31);
        LocalDateTime attendanceTimeFive = LocalDateTime.of(2025, 2, 28, 13, 31);
        LocalDateTime attendanceTimeSix = LocalDateTime.of(2025, 3, 1, 13, 31);

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord();
        attendanceRecord.addAttendanceTime(attendanceTimeOne);
        attendanceRecord.addAttendanceTime(attendanceTimeTwo);
        attendanceRecord.addAttendanceTime(attendanceTimeThree);
        attendanceRecord.addAttendanceTime(attendanceTimeFour);
        attendanceRecord.addAttendanceTime(attendanceTimeFive);
        attendanceRecord.addAttendanceTime(attendanceTimeSix);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.EXPULSION);
    }
}
