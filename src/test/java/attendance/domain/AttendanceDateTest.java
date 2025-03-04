package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AttendanceDateTest {

    @ParameterizedTest
    @CsvSource({
            "2024-12-02, 2024-12-03",
            "2024-12-13, 2024-12-16",
            "2024-12-24, 2024-12-26",
    })
    void 출석이_가능한_다음_날짜로_넘어간다(LocalDate currentDate, LocalDate expected) {
        //given
        AttendanceDate attendanceDate = new AttendanceDate(currentDate);

        //when
        AttendanceDate nextAttendanceDate = attendanceDate.nextDate();

        //then
        assertThat(nextAttendanceDate).isEqualTo(new AttendanceDate(expected));
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-14",
            "2024-12-15",
    })
    void 주말에_출석날짜를_생성할_경우_예외가_발생한다(LocalDate date) {
        // when & then
        assertThatThrownBy(() -> new AttendanceDate(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말에는 출석할 수 없습니다.");
    }

    @Test
    void 공휴일에_출석날짜를_생성할_경우_예외가_발생한다() {
        //given
        LocalDate date = LocalDate.of(2024, 12, 25);

        // when & then
        assertThatThrownBy(() -> new AttendanceDate(date))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-03, 2024-12-04, true",
            "2024-12-04, 2024-12-04, true",
            "2024-12-04, 2024-12-03, false",
    })
    void 날짜가_같거나_이전이면_true를_반환한다(LocalDate date1, LocalDate date2, boolean expected) {
        //given
        AttendanceDate attendanceDate = new AttendanceDate(date1);

        //when
        boolean actual = attendanceDate.isBeforeAndEqual(date2);

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
