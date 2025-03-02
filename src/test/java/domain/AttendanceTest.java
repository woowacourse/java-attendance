package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import util.DayConverter;

class AttendanceTest {

    @ParameterizedTest
    @DisplayName("출석 시간에 따라 정상 출석인지 확인합니다.")
    @ValueSource(strings = {"10:00", "09:00", "10:05"})
    void attendancePresentTest(String value) {
        Time time = new Time(value);
        LocalDate today = LocalDate.of(2024, 12, 3); // 화요일

        Attendance attendance = new Attendance(DayConverter.combineTimeAndDate(time, today));

        Assertions.assertEquals(AttendanceCode.PRESENT, attendance.calculateAttendanceCode());
    }

    @ParameterizedTest
    @DisplayName("출석 시간에 따라 지각인지 확인합니다.")
    @ValueSource(strings = {"10:06", "10:30", "10:15"})
    void attendanceLateTest(String value) {
        Time time = new Time(value);
        LocalDate today = LocalDate.of(2024, 12, 3); // 화요일

        Attendance attendance = new Attendance(DayConverter.combineTimeAndDate(time, today));

        Assertions.assertEquals(AttendanceCode.LATE, attendance.calculateAttendanceCode());
    }

    @ParameterizedTest
    @DisplayName("출석 시간에 따라 결석인지 확인합니다.")
    @ValueSource(strings = {"10:31", "10:59", "11:00", "23:00"})
    void attendanceAbsentTest(String value) {
        Time time = new Time(value);
        LocalDate today = LocalDate.of(2024, 12, 3); // 화요일

        Attendance attendance = new Attendance(DayConverter.combineTimeAndDate(time, today));

        Assertions.assertEquals(AttendanceCode.ABSENT, attendance.calculateAttendanceCode());
    }

    @Test
    @DisplayName("공휴일에 출석을 시도하면 예외가 발생합니다.")
    void attendanceInHolidayTest() {
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 25, 10, 0);
        List<Integer> holidays = List.of(25);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Attendance(attendanceTime).validateHoliday(holidays));
    }

    @Test
    @DisplayName("출석이 특정 날짜와 동일하면 참을 반환합니다.")
    void isSameDayTest() {
        LocalDateTime testDate = LocalDateTime.of(2024, 12, 2, 10, 0);
        Attendance attendance = new Attendance(testDate);

        Assertions.assertTrue(attendance.isSameDay(testDate.toLocalDate()));

    }
}