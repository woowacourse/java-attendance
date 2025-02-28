import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceTime;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @CsvSource({
            "7, 59", "23, 1"
    })
    void 출석시간_객체를_생성할_때_운영시간이_아닌_경우_예외를_발생시킨다(int hour, int minute) {
        //given
        LocalDateTime checkInTime = LocalDateTime.of(2024, 12, 2, hour, minute);
        //when & then
        assertThatThrownBy(() -> new AttendanceTime(checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("운영시간이 아니면 출석할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "12, 1", "12, 25"
    })
    void 출석시간을_생성할_때_휴일인_경우_예외를_발생시킨다(int month, int date) {
        //given
        LocalDateTime checkInTime = LocalDateTime.of(2024, month, date, 9, 30);
        //when & then
        assertThatThrownBy(() -> new AttendanceTime(checkInTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말과 공휴일에는 출석할 수 없습니다.");
    }

    @ParameterizedTest
    @CsvSource({
            "2, 9, 30, 2, 10, 30, true",
            "2, 9, 30, 3, 10, 30, false"
    })
    void 다른_출석시간과_같은_날인지_비교한다(int date1, int hour1, int minute1, int date2, int hour2, int minute2, boolean expected) {
        //given
        LocalDateTime time1 = LocalDateTime.of(2024, 12, date1, hour1, minute1);
        LocalDateTime time2 = LocalDateTime.of(2024, 12, date2, hour2, minute2);
        AttendanceTime attendanceTime1 = new AttendanceTime(time1);
        AttendanceTime attendanceTime2 = new AttendanceTime(time2);
        //when
        boolean isSameDay = attendanceTime1.isSameDay(attendanceTime2);
        //then
        assertThat(expected).isEqualTo(isSameDay);
    }

    @ParameterizedTest
    @CsvSource({
            "2024, 12, 2, false",
            "2024, 12, 3, true",
            "2024, 11, 2, false",
            "2023, 12, 2, false"
    })
    void 출석시간이_특정날짜인지_확인한다(int year, int month, int date, boolean expected) {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 9, 30);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        //when
        boolean actual = attendanceTime.isIn(LocalDate.of(year, month, date));
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 출석시간을_변경한다() {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, 9, 30);
        LocalTime newTime = LocalTime.of(8, 30);
        AttendanceTime expected = new AttendanceTime(LocalDateTime.of(time.toLocalDate(), newTime));
        AttendanceTime attendanceTime = new AttendanceTime(time);
        //when
        AttendanceTime newAttendanceTime = attendanceTime.changeTime(newTime);
        //then
        assertThat(newAttendanceTime).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
            "10, 0, 0, 0, 0",
            "10, 0, 0, 1, 1",
            "9, 59, 59, 999999999, -1"
    })
    void 출석시간과_출석인정시간의_차이를_계산한다(int hour, int minute, int second, int nano, long expected) {
        //given
        LocalDateTime time = LocalDateTime.of(2024, 12, 3, hour, minute, second, nano);
        AttendanceTime attendanceTime = new AttendanceTime(time);
        //when
        long differenceNanos = attendanceTime.calculateDifferenceFromAttendanceStandard();
        //then
        assertThat(differenceNanos).isEqualTo(expected);
    }
}
