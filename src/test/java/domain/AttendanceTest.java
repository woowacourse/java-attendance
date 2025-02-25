package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @DisplayName("출석 시간이 5분 이하면 출석이다")
    @Test
    void attendancePresentTest() {
        LocalDateTime originalTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Attendance attendance = new Attendance(originalTime);
        Assertions.assertSame(attendance.calculateAttendanceStatus(), AttendanceStatus.PRESENT);
    }

    @DisplayName("출석 시간이 5분 초과, 30분 이하면 지각이다")
    @Test
    void attendanceLateTest() {
        LocalDateTime originalTime = LocalDateTime.of(2024, 12, 2, 13, 6);
        Attendance attendance = new Attendance(originalTime);
        Assertions.assertSame(attendance.calculateAttendanceStatus(), AttendanceStatus.LATE);
    }

    @DisplayName("출석 시간이 30분 초과면 결석한다")
    @Test
    void attendanceAbsentTest() {
        LocalDateTime originalTime = LocalDateTime.of(2024, 12, 2, 13, 31);
        Attendance attendance = new Attendance(originalTime);
        Assertions.assertSame(attendance.calculateAttendanceStatus(), AttendanceStatus.ABSENT);
    }

    @DisplayName("int형 정수와 날짜의 dayOfMonth가 같으면 true, 다르면 false를 반환합니다.")
    @Test
    void isSameDayTest() {
        LocalDateTime day = LocalDateTime.of(2024, 12, 2, 13, 0);
        LocalDate sameDay = LocalDate.of(2024, 12, 2);
        LocalDate differentDay = LocalDate.of(2024, 12, 10);

        Attendance attendance = new Attendance(day);
        Assertions.assertAll(
                () -> Assertions.assertTrue(attendance.isSameDay(sameDay)),
                () -> Assertions.assertFalse(attendance.isSameDay(differentDay))
        );
    }
}