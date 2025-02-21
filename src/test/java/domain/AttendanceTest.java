package domain;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceTest {
    @DisplayName("출석 시간이 5분 이하면 출석합니다.")
    @Test
    void attendancePresentTest() {
        LocalDateTime originalTime = LocalDateTime.of(2024, 12, 2, 13, 0);
        Attendance attendance = new Attendance(originalTime);
        Assertions.assertSame(attendance.calculateAttendanceStatus(), AttendanceStatus.PRESENT);
    }

    @DisplayName("출석 시간이 5분 초과, 30분 이하면 지각입니다.")
    @Test
    void attendanceLateTest() {
        LocalDateTime originalTime = LocalDateTime.of(2024, 12, 2, 13, 6);
        Attendance attendance = new Attendance(originalTime);
        Assertions.assertSame(attendance.calculateAttendanceStatus(), AttendanceStatus.LATE);
    }

    @DisplayName("출석 시간이 30분 초과면 결석입니다.")
    @Test
    void attendanceAbsentTest() {
        LocalDateTime originalTime = LocalDateTime.of(2024, 12, 2, 13, 31);
        Attendance attendance = new Attendance(originalTime);
        Assertions.assertSame(attendance.calculateAttendanceStatus(), AttendanceStatus.ABSENT);
    }

    @DisplayName("날짜가 일치하면 true를 반환합니다.")
    @Test
    void isSameDayTrueTest() {
        LocalDateTime day = LocalDateTime.of(2024, 12, 2, 13, 0);
        int sameDay = 2;

        Attendance attendance = new Attendance(day);
        Assertions.assertTrue(attendance.isSameDay(sameDay));
    }

    @DisplayName("날짜가 일치하면 False를 반환합니다.")
    @Test
    void isSameDayFalseTest() {
        LocalDateTime day = LocalDateTime.of(2024, 12, 2, 13, 0);
        int sameDay = 10;

        Attendance attendance = new Attendance(day);
        Assertions.assertFalse(attendance.isSameDay(sameDay));
    }


    @DisplayName("일(날짜)을 반환합니다.")
    @Test
    void getDayTest() {
        LocalDateTime day = LocalDateTime.of(2024, 12, 2, 13, 0);
        int sameDay = 2;

        Attendance attendance = new Attendance(day);
        Assertions.assertEquals(sameDay, attendance.getDay());
    }
}