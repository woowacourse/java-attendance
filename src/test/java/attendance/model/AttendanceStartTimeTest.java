package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("교육 시작 시간 테스트")
class AttendanceStartTimeTest {

    @DisplayName("각 요일마다 출석 시작 시간을 반환한다.")
    @ParameterizedTest
    @CsvSource({
            "MONDAY, 13:00",
            "TUESDAY, 10:00",
            "WEDNESDAY, 10:00",
            "THURSDAY, 10:00",
            "FRIDAY, 10:00",
    })
    void findDayOfWeekTest(DayOfWeek dayOfWeek, LocalTime startTime) {
        LocalTime attendanceStartTime = AttendanceStartTime.findDayOfWeek(dayOfWeek);
        assertThat(attendanceStartTime)
                .isEqualTo(startTime);
    }

    @DisplayName("주말의 출석 시작 시간을 찾으려고 할 때 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "SATURDAY",
            "SUNDAY"
    })
    void shouldThrowException_WhenFindStartTimeOfWeekend(DayOfWeek dayOfWeek) {
        assertThatThrownBy(() -> AttendanceStartTime.findDayOfWeek(dayOfWeek))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당하는 요일의 출석 시작 시간을 찾을 수 없습니다.");
    }
}
