package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatusTest {
    @DisplayName("교육 시작 시간과 출석 시간이 주어졌을 때 (출석) 상태를 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"9, 0", "10, 5"})
    void test1(int hour, int minute) {
        // given
        LocalTime educationTime = LocalTime.of(10, 0);
        LocalTime enterTime = LocalTime.of(hour, minute);

        // when
        AttendanceStatus status = AttendanceStatus.from(educationTime, enterTime);

        // then
        assertThat(status).isSameAs(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("교육 시작 시간과 출석 시간이 주어졌을 때 (지각) 상태를 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 6", "10, 30"})
    void test2(int hour, int minute) {
        // given
        LocalTime educationTime = LocalTime.of(10, 0);
        LocalTime enterTime = LocalTime.of(hour, minute);

        // when
        AttendanceStatus status = AttendanceStatus.from(educationTime, enterTime);

        // then
        assertThat(status).isSameAs(AttendanceStatus.LATE);
    }

    @DisplayName("교육 시작 시간과 출석 시간이 주어졌을 때 (결석) 상태를 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"10, 31"})
    void test3(int hour, int minute) {
        // given
        LocalTime educationTime = LocalTime.of(10, 0);
        LocalTime enterTime = LocalTime.of(hour, minute);

        // when
        AttendanceStatus status = AttendanceStatus.from(educationTime, enterTime);

        // then
        assertThat(status).isSameAs(AttendanceStatus.ABSENCE);
    }
}
