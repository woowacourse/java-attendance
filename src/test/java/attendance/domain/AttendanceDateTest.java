package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceDateTest {

    @CsvSource(value = {
            "2,22,토", "2,23,일", "12,25,목"
    })
    @ParameterizedTest
    void 등교일이_아닌_경우_출석_날짜를_생성할_수_없다(int month, int day, String dayOfWeek) {
        assertThatThrownBy(() -> new AttendanceDate(LocalDate.of(2025, month, day)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%d월 %d일 %s요일은 등교일이 아닙니다.", month, day, dayOfWeek);
    }

    @Test
    void 등교일이면_출석_날짜를_생성한다() {
        LocalDate localDate = LocalDate.of(2025, 2, 25);

        AttendanceDate attendanceDate = new AttendanceDate(localDate);

        assertThat(attendanceDate).isEqualTo(new AttendanceDate(LocalDate.of(2025, 2, 25)));
    }

    @CsvSource(value = {
            "26,true", "27,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_날짜와_같은지_알려준다(int day, boolean expected) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2025, 2, 26));

        assertThat(attendanceDate.isSameDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @CsvSource(value = {
            "26,true", "25,false"
    })
    @ParameterizedTest
    void 날짜를_알려주면_출석_날짜가_과거이거나_같은_날짜인지_알려준다(int day, boolean expected) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2025, 2, 26));

        assertThat(attendanceDate.isBeforeOrEqualDate(LocalDate.of(2025, 2, day))).isEqualTo(expected);
    }

    @CsvSource(value = {
            "24,true", "25,false"
    })
    @ParameterizedTest()
    void 날짜를_알려주면_출석_날짜가_월요일인지_알려준다(int day, boolean expected) {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2025, 2, day));

        assertThat(attendanceDate.isMonday()).isEqualTo(expected);
    }

}
