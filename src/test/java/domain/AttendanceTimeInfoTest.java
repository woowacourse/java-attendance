package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceTimeInfoTest {
    @Test
    @DisplayName("월요일의 지각 시간을 반환한다")
    void should_return_Late_LocalTime_by_monday() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

        // when
        LocalTime result = AttendanceTimeInfo.getLateLocalTime(dayOfWeek);

        // then
        assertThat(result).isEqualTo(LocalTime.of(13, 5));
    }

    @Test
    @DisplayName("월요일의 결석 시간을 반환한다")
    void should_return_ABSENCE_LocalTime_by_monday() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;

        // when
        LocalTime result = AttendanceTimeInfo.getAbsenceTime(dayOfWeek);

        // then
        assertThat(result).isEqualTo(LocalTime.of(13, 30));
    }

    @ParameterizedTest
    @DisplayName("화-금요일의 지각 시간을 반환한다")
    @CsvSource(value = {"TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"})
    void should_return_LATE_LocalTime_by_monday(DayOfWeek dayOfWeek) {
        // when
        LocalTime result = AttendanceTimeInfo.getLateLocalTime(dayOfWeek);

        // then
        assertThat(result).isEqualTo(LocalTime.of(10, 5));
    }

    @ParameterizedTest
    @DisplayName("화-금요일의 결석 시간을 반환한다")
    @CsvSource(value = {"TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY"})
    void should_return_ABSENCE_LocalTime_by_monday(DayOfWeek dayOfWeek) {
        // when
        LocalTime result = AttendanceTimeInfo.getAbsenceTime(dayOfWeek);

        // then
        assertThat(result).isEqualTo(LocalTime.of(10, 30));
    }
}
