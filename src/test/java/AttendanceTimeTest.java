import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.AttendanceTime;
import java.time.LocalDateTime;
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
}
