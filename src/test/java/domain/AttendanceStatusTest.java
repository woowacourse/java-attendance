package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatusTest {
    @DisplayName("출석 시간이 교육 시작 시간으로부터 5분 이내일 경우 출석을 반환한다.")
    @Test
    void presentReturnTest() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, 5);

        // when
        AttendanceStatus expectedValue = AttendanceStatus.PRESENT;
        AttendanceStatus actualValue = AttendanceStatus.getStatus(dayOfWeek, time);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("출석 시간이 교육 시작 시간으로부터 5분 초과 30분 이내일 경우 지각을 반환한다.")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void tardyReturnTest(int minute) {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, minute);

        // when
        AttendanceStatus expectedValue = AttendanceStatus.TARDY;
        AttendanceStatus actualValue = AttendanceStatus.getStatus(dayOfWeek, time);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("출석 시간이 교육 시작 시간으로부터 30분 초과할 경우 결석을 반환한다.")
    @Test
    void absentReturnTest() {
        // given
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, 31);

        // when
        AttendanceStatus expectedValue = AttendanceStatus.ABSENT;
        AttendanceStatus actualValue = AttendanceStatus.getStatus(dayOfWeek, time);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
