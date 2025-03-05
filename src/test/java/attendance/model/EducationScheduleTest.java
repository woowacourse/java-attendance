package attendance.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.Locale;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.JavaTimeConversionPattern;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayName("교육 일정 테스트")
class EducationScheduleTest {

    @DisplayName("요일로 교육 시작 시간을 확인할 수 있다.")
    @ParameterizedTest
    @CsvSource({
            "MONDAY, 13:00",
            "TUESDAY, 10:00",
            "WEDNESDAY, 10:00",
            "THURSDAY, 10:00",
            "FRIDAY, 10:00"
    })
    void findStartTimeByDayTest(DayOfWeek day, @JavaTimeConversionPattern("HH:mm") LocalTime expected) {
        // when
        LocalTime startTime = EducationSchedule.findStartTimeByDay(day);

        // then
        assertThat(startTime)
                .isEqualTo(expected);
    }

    @DisplayName("교육 시작 시간을 확인할 때 스케줄이 존재하지 않는 요일인 경우 예외가 발생한다.")
    @ParameterizedTest
    @CsvSource({
            "SATURDAY",
            "SUNDAY"
    })
    void shouldThrowException_WhenDayHasNoSchedule(DayOfWeek day) {
        // when & then
        assertThatCode(() -> EducationSchedule.findStartTimeByDay(day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 요일의 교육 일정이 없습니다. 입력: %s".formatted(day.getDisplayName(TextStyle.FULL, Locale.KOREAN)));
    }
}
