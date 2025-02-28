package domain;

import java.time.LocalDateTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStatusTest {
    @DisplayName("월요일 13시 5분 이내 출석하면 출석으로 인정된다")
    @Test
    void test() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2025, 2, 24, 13, 5);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateStatus(attendanceTime);

        // then
        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("월요일 13시 5분 초과 13시 30분 이내 출석하면 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void test2(int minute) {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2025, 2, 24, 13, minute);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateStatus(attendanceTime);

        // then
        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.TARDY);
    }

    @DisplayName("월요일 13시 30분 초과 출석하면 결석이다")
    @Test
    void test3() {
        // given
        LocalDateTime attendanceTime = LocalDateTime.of(2025, 2, 24, 13, 31);

        // when
        AttendanceStatus attendanceStatus = AttendanceStatus.calculateStatus(attendanceTime);

        // then
        Assertions.assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }
}