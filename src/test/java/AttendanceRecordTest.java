import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

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
        String time = "09:59";

        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        LocalDateTime attendanceTime = attendanceRecord.attend(time);

        assertThat(attendanceTime).isEqualTo(LocalDateTime.of(
                weekday,
                LocalTime.of(
                        Integer.parseInt(time.split(":")[0]),
                        Integer.parseInt(time.split(":")[1])
                )));
    }

    @ParameterizedTest
    @CsvSource(value = {"09:59, 출석", "10:06, 지각", "10:31, 결석"})
    void 해당_날짜의_출석_지각_결석_여부를_판단한다(String time, String expected) {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        LocalDateTime attendanceTime = attendanceRecord.attend(time);

        AttendanceStatus status = attendanceRecord.getAttendanceStatus(attendanceTime.getDayOfMonth());
        assertThat(status.getStatus()).isEqualTo(expected);
    }

    @Test
    void 해당_날짜의_출석_시간을_확인한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        LocalDateTime attendanceTime = attendanceRecord.attend("09:59");

        LocalDateTime targetAttendanceTime = attendanceRecord.findAttendanceTimeByDay(attendanceTime.getDayOfMonth());
        assertThat(targetAttendanceTime).isEqualTo(attendanceTime);
    }

    @Test
    void 해당_날짜의_출석_시간이_없으면_예외가_발생한다() {
        AttendanceRecord attendanceRecord = new AttendanceRecord(() -> weekday);
        attendanceRecord.attend("09:59");

        assertThatThrownBy(() -> attendanceRecord.findAttendanceTimeByDay(1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 날짜의 출석 시간이 없습니다.");
    }

    @Test
    void 출석_날짜가_주말이면_예외가_발생한다() {
        assertThatThrownBy(() -> new AttendanceRecord(() -> weekend).attend("09:59"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 주말에는 출석할 수 없습니다.");
    }

}
