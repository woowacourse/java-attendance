package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AttendanceRecordTest {

    @Test
    public void 출석_기록_추가() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9, 59);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(localDateTime));

        //when & then
        assertDoesNotThrow(() -> new AttendanceRecord(attendanceTimes));
    }

    @Test
    public void 원본_리스트_수정시_내부_리스트_영향_없음() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9, 59);
        LocalDateTime localDateTime2 = LocalDateTime.of(2025, 2, 28, 10, 31);
        List<AttendanceTime> originalList = new ArrayList<>();
        originalList.add(new AttendanceTime(localDateTime));
        originalList.add(new AttendanceTime(localDateTime2));

        AttendanceRecord attendanceRecord = new AttendanceRecord(originalList);

        //when
        originalList.add(new AttendanceTime(LocalDateTime.of(2025, 3, 1, 10, 31)));

        //then
        assertEquals(2, attendanceRecord.getAttendanceRecord().size());
    }

    @Test
    public void 패널티_없는_상태_반환_결석_1회() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(attendanceTimeOne));
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.NONE);
    }

    @Test
    public void 패널티_없는_상태_반환_결석_0회() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 12, 31);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(attendanceTimeOne));
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.NONE);
    }

    @Test
    public void 패널티_경고_상태_반환() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        LocalDateTime attendanceTimeTwo = LocalDateTime.of(2025, 2, 25, 10, 31);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(attendanceTimeOne),
                new AttendanceTime(attendanceTimeTwo));
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.WARNING);
    }

    @Test
    public void 패널티_면담_상태_반환() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        LocalDateTime attendanceTimeTwo = LocalDateTime.of(2025, 2, 25, 10, 31);
        LocalDateTime attendanceTimeThree = LocalDateTime.of(2025, 2, 26, 10, 31);

        List<AttendanceTime> attendanceTimes = List.of(
                new AttendanceTime(attendanceTimeOne),
                new AttendanceTime(attendanceTimeTwo),
                new AttendanceTime(attendanceTimeThree)
        );
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

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

        List<AttendanceTime> attendanceTimes = List.of(
                new AttendanceTime(attendanceTimeOne),
                new AttendanceTime(attendanceTimeTwo),
                new AttendanceTime(attendanceTimeThree),
                new AttendanceTime(attendanceTimeFour),
                new AttendanceTime(attendanceTimeFive),
                new AttendanceTime(attendanceTimeSix)
        );

        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.EXPULSION);
    }
}
