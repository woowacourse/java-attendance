package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceTimeTest {

    @CsvSource(value = {"7,59", "23,1"})
    @ParameterizedTest
    void 캠퍼스_운영_시간이_아니면_출석_시간을_생성할_수_없다(int hour, int minute) {
        assertThatThrownBy(() -> new AttendanceTime(LocalTime.of(hour, minute)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%02d:%02d은 출석 시간이 아닙니다.", hour, minute);
    }

    @CsvSource(value = {"8,0", "23,0"})
    @ParameterizedTest
    void 등교_시간을_알려주면_출석_시간을_생성한다(int hour, int minute) {
        LocalTime localTime = LocalTime.of(hour, minute);

        AttendanceTime attendanceTime = new AttendanceTime(localTime);

        assertThat(attendanceTime).isEqualTo(new AttendanceTime(LocalTime.of(hour, minute)));
    }

}
