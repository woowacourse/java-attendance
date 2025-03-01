package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    @Test
    void 출석_기록_추가() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9, 59);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(localDateTime));

        //when & then
        assertDoesNotThrow(() -> new AttendanceRecord(attendanceTimes));
    }

    @Test
    void 원본_리스트_수정시_내부_리스트_영향_없음() {
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
        Assertions.assertThat(attendanceRecord.getAttendanceRecord().size()).isEqualTo(2);
    }

    @Test
    void 내부_리스트_수정_불가능_확인() {
        //given
        LocalDateTime localDateTime = LocalDateTime.of(2025, 2, 27, 9, 59);
        LocalDateTime localDateTime2 = LocalDateTime.of(2025, 2, 28, 10, 31);
        List<AttendanceTime> originalList = new ArrayList<>();
        originalList.add(new AttendanceTime(localDateTime));
        originalList.add(new AttendanceTime(localDateTime2));

        AttendanceRecord attendanceRecord = new AttendanceRecord(originalList);

        //when & then
        assertThrows(UnsupportedOperationException.class, () -> {
            attendanceRecord.getAttendanceRecord().add(new AttendanceTime(LocalDateTime.of(2025, 3, 1, 10, 31)));
        });
    }

    @Test
    void 패널티_없는_상태_반환_결석_1회() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 13, 31);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(attendanceTimeOne));
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.NONE);
    }

    @Test
    void 패널티_없는_상태_반환_결석_0회() {
        //given
        LocalDateTime attendanceTimeOne = LocalDateTime.of(2025, 2, 24, 12, 31);
        List<AttendanceTime> attendanceTimes = List.of(new AttendanceTime(attendanceTimeOne));
        //when
        AttendanceRecord attendanceRecord = new AttendanceRecord(attendanceTimes);

        //then
        Assertions.assertThat(attendanceRecord.checkPenaltyStatus()).isEqualTo(PenaltyType.NONE);
    }

    @Test
    void 패널티_경고_상태_반환() {
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
    void 패널티_면담_상태_반환() {
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
    void 패널티_제적_상태_반환() {
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

    @DisplayName("출석 등록할 때")
    @Test
    void 출석_기록이_있으면_예외_발생() {
        //given
        LocalDateTime inputTime = LocalDateTime.of(2025, 2, 28, 9, 59);

        AttendanceRecord attendanceRecord = new AttendanceRecord(
                List.of(new AttendanceTime(LocalDateTime.of(2025, 2, 28, 10, 31)))
        );

        //when & then
        Assertions.assertThatThrownBy(() -> attendanceRecord.registerAttendance(inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("이미 출석 기록이 있습니다.");
    }

    @DisplayName("출석 수정할 때")
    @Test
    void 출석_기록이_없으면_예외_발생() {
        //given
        LocalDateTime inputTime = LocalDateTime.of(2025, 2, 28, 9, 59);

        AttendanceRecord attendanceRecord = new AttendanceRecord(
                List.of(new AttendanceTime(LocalDateTime.of(2025, 2, 27, 9, 59)))
        );

        //when & then
        Assertions.assertThatThrownBy(() -> attendanceRecord.findAttendanceRecord(inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 없습니다.");
    }
}
