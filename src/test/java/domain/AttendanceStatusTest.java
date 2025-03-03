package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStatusTest {
    @DisplayName("기준 시간에서 5분 이하는 출석이다")
    @ParameterizedTest
    @ValueSource(ints = {0, 4, 5})
    void presentTest(int minute) {
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 13, minute);
        Assertions.assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.calculateAttendanceStatus(time));
    }

    @DisplayName("기준 시간에서 5분 초과 30분 이하는 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 20, 30})
    void lateTest(int minute) {
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 13, minute);
        Assertions.assertEquals(AttendanceStatus.LATE, AttendanceStatus.calculateAttendanceStatus(time));
    }

    @DisplayName("기준 시간에서 30분 초과는 결석이다")
    @ParameterizedTest
    @ValueSource(ints = {31, 35, 59})
    void absentTest(int minute) {
        LocalDateTime time = LocalDateTime.of(2024, 12, 2, 13, minute);
        Assertions.assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.calculateAttendanceStatus(time));
    }
}