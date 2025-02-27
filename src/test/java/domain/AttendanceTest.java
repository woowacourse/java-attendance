package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class AttendanceTest {
    @DisplayName("평일이고, 캠퍼스가 연 시간인 경우 출석을 저장한다")
    @Test
    void saveAttendanceTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Assertions.assertDoesNotThrow(() -> new Attendance(attendanceTime));
    }

    @DisplayName("평일이 아니라면 예외를 발생시킨다.")
    @ParameterizedTest
    @ValueSource(ints = {1, 7, 8, 25})
    void notWeekdayTest(int day) {
        LocalDateTime holiday = LocalDateTime.of(2024, 12, day, 13, 0);
        Assertions.assertThrows(IllegalArgumentException.class, () -> new Attendance(holiday));
    }

    @DisplayName("기준 시간에서 5분 이하는 출석이다")
    @ParameterizedTest
    @ValueSource(ints = {0, 4, 5})
    void presentTest(int minute) {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, minute));
        Assertions.assertEquals(AttendanceStatus.PRESENT, attendance.calculateAttendanceStatus());
    }

    @DisplayName("기준 시간에서 5분 초과 30분 이하는 지각이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 20, 30})
    void lateTest(int minute) {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, minute));
        Assertions.assertEquals(AttendanceStatus.LATE, attendance.calculateAttendanceStatus());
    }

    @DisplayName("기준 시간에서 30분 초과는 결석이다")
    @ParameterizedTest
    @ValueSource(ints = {31, 35, 59})
    void absentTest(int minute) {
        Attendance attendance = new Attendance(LocalDateTime.of(2024, 12, 2, 13, minute));
        Assertions.assertEquals(AttendanceStatus.ABSENT, attendance.calculateAttendanceStatus());
    }
}
