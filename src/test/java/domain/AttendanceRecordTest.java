package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest extends BaseAttendanceTest {

    @Test
    void 출석하면_출석_시간을_추가한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateProviderDec13);
        LocalDateTime attendanceTime = attendanceRecord.attend(todayTime);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(DEC_13, todayTime));
    }

    @Test
    void 해당_날짜의_출석_시간을_확인한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateProviderDec13);
        LocalDateTime attendanceTime = attendanceRecord.attend(todayTime);

        LocalDateTime targetAttendanceTime = attendanceRecord.findAttendanceTimeByDay(attendanceTime.getDayOfMonth());
        assertThat(targetAttendanceTime).isEqualTo(attendanceTime);
    }

    @Test
    void 해당_날짜의_출석_시간이_없으면_예외가_발생한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateProviderDec13);
        attendanceRecord.attend(todayTime);

        assertThatThrownBy(() -> attendanceRecord.findAttendanceTimeByDay(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 날짜의 출석 시간이 없습니다.");
    }

    @Test
    void 이미_출석한_날짜이면_예외가_발생한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateProviderDec13);
        attendanceRecord.attend(todayTime);

        assertThatThrownBy(() -> attendanceRecord.attend(todayTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 날짜에는 이미 출석했습니다. 수정 기능을 이용해주세요.");
    }

    @Test
    void 수정하려는_날짜의_출석_시간을_수정한다() {
        LocalTime time = LocalTime.of(9, 58);
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateProviderDec13);
        attendanceRecord.attend(time);
        int modifyDay = 13;
        LocalTime modifyTime = LocalTime.of(10, 31);

        attendanceRecord.modifyAttendanceTime(modifyDay, modifyTime);

        assertThat(attendanceRecord.findAttendanceTimeByDay(modifyDay).toLocalTime()).isEqualTo(modifyTime);
    }

    @Test
    void 출석_지각_결석_횟수를_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> LocalDate.of(2024, 12, 4));
        attendanceRecord.add(LocalDateTime.of(2024, 12, 2, 10, 5));
        attendanceRecord.add(LocalDateTime.of(2024, 12, 3, 10, 6));
        attendanceRecord.add(LocalDateTime.of(2024, 12, 4, 10, 31));

        assertThat(attendanceRecord.countStatus(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
        assertThat(attendanceRecord.countStatus(AttendanceStatus.LATE)).isEqualTo(1);
        assertThat(attendanceRecord.countStatus(AttendanceStatus.ABSENCE)).isEqualTo(1);
    }

    @Test
    void 해당_날짜에_결석인지_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(dateProviderDec13);
        attendanceRecord.add(LocalDateTime.of(2024, 12, 13, 10, 0));

        assertThat(attendanceRecord.isAbsent(DEC_13)).isFalse();
        assertThat(attendanceRecord.isAbsent(DEC_14)).isTrue();
    }

}
