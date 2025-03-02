import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AttendanceRecordTest {

    private LocalDate weekday;
    private LocalDate weekend;

    @BeforeEach
    void setUp() {
        weekday = LocalDate.of(2024, 12, 13);
        weekend = LocalDate.of(2024, 12, 14);
    }

    @Test
    void 출석하면_출석_시간을_추가한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        LocalDateTime attendanceTime = attendanceRecord.attend(todayTime);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(weekday, todayTime));
    }

    @Test
    void 해당_날짜의_출석_시간을_확인한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        LocalDateTime attendanceTime = attendanceRecord.attend(todayTime);

        LocalDateTime targetAttendanceTime = attendanceRecord.findAttendanceTimeByDay(attendanceTime.getDayOfMonth());
        assertThat(targetAttendanceTime).isEqualTo(attendanceTime);
    }

    @Test
    void 해당_날짜의_출석_시간이_없으면_예외가_발생한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        attendanceRecord.attend(todayTime);

        assertThatThrownBy(() -> attendanceRecord.findAttendanceTimeByDay(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 날짜의 출석 시간이 없습니다.");
    }

    @Test
    void 이미_출석한_날짜이면_예외가_발생한다() {
        LocalTime todayTime = LocalTime.of(9, 59);
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        attendanceRecord.attend(todayTime);

        assertThatThrownBy(() -> attendanceRecord.attend(todayTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 날짜에는 이미 출석했습니다. 수정 기능을 이용해주세요.");
    }

}
