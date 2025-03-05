package attendance.domain;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalTime;

import org.junit.jupiter.api.Test;
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

    @CsvSource(value = {
            "10,0,true","18,0,true",
            "9,59,false","18,1,false"
    })
    @ParameterizedTest
    void 시작_시간과_끝_시간을_알려주면_출석_시간이_사이에_존재하는지_알려준다(int hour, int minute, boolean expected) {
        LocalTime startInclusive = LocalTime.of(10, 0);
        LocalTime endInclusive = LocalTime.of(18, 0);
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(hour, minute));

        assertThat(attendanceTime.isBetweenInclusive(startInclusive, endInclusive)).isEqualTo(expected);
    }

}
