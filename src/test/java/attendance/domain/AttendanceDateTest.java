package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceDateTest {

    @Test
    void 주말에_출석하면_출석이_불가능하다() {
        assertThatThrownBy(() -> new AttendanceDate(LocalDate.of(2025, 2, 15)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2월 15일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 공휴일에_출석하면_출석이_불가능하다() {
        assertThatThrownBy(() -> new AttendanceDate(LocalDate.of(2025, 1, 1)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("1월 1일 수요일은 등교일이 아닙니다.");
    }

    @CsvSource(value = {
            "13,false", "14,true"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_날짜와_같은지_알려준다(int day, boolean expected) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2025, 2, 14));

        assertThat(attendanceDate.isSameDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

}
