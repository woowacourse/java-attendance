package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceStatusTest {
    @DisplayName("기준 시간에서 5분 이하인 경우 출석입니다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 5})
    void calculateAttendancePresentStatusTest(int minute) {
        LocalDateTime present = LocalDateTime.of(2024,12,3,10,minute);

        Assertions.assertEquals(AttendanceStatus.PRESENT, AttendanceStatus.calculateAttendanceStatus(present));
    }

    @DisplayName("기준 시간에서 5분 초과 30분 이하인 경우 지각입니다.")
    @ParameterizedTest
    @ValueSource(ints = {6, 30})
    void calculateAttendanceLateStatusTest(int minute) {
        LocalDateTime present = LocalDateTime.of(2024,12,3,10, minute);

        Assertions.assertEquals(AttendanceStatus.LATE, AttendanceStatus.calculateAttendanceStatus(present));
    }

    @DisplayName("기준 시간에서 30분 초과인 경우 결석입니다.")
    @ParameterizedTest
    @ValueSource(ints = {31, 59})
    void calculateAttendanceAbsentStatusTest(int minute) {
        LocalDateTime present = LocalDateTime.of(2024,12,3,10,minute);

        Assertions.assertEquals(AttendanceStatus.ABSENT, AttendanceStatus.calculateAttendanceStatus(present));
    }
}