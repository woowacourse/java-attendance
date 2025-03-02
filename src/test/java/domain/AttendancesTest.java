package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    private List<Attendance> testAttendance = List.of(
            new Attendance(LocalDateTime.of(2024, 12, 2, 13, 0)),
            new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0)),
            new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)),
            new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)),
            new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)),
            new Attendance(LocalDateTime.of(2024, 12, 9, 13, 6)),
            new Attendance(LocalDateTime.of(2024, 12, 10, 10, 6)),
            new Attendance(LocalDateTime.of(2024, 12, 11, 10, 6)),
            new Attendance(LocalDateTime.of(2024, 12, 12, 10, 31)),
            new Attendance(LocalDateTime.of(2024, 12, 13, 10, 31)));

    private Attendances attendances;

    @BeforeEach
    void makeTestAttendances() {
        attendances = new Attendances(testAttendance);
    }

    @Test
    @DisplayName("특정 날짜의 출석을 불러오는지 확인합니다.")
    void getSpecificAttendanceTest() {
        DayOfMonth dayOfMonth = new DayOfMonth(3); // 불러올 날짜

        Assertions.assertEquals(new Attendance(LocalDateTime.of(2024, 12, 3, 10, 0)),
                attendances.getSpecificAttendance(dayOfMonth, LocalDate.of(2024, 12, 6))
        );
    }

    @Test
    @DisplayName("미래 날짜를 불러올 시 예외가 발생합니다.")
    void validateFutureDateTest() {
        DayOfMonth dayOfMonth = new DayOfMonth(10); // 불러올 날짜

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> attendances.getSpecificAttendance(dayOfMonth, LocalDate.of(2024, 12, 6)));
    }

    @Test
    @DisplayName("특정 날짜의 출석을 특정 시간으로 변경합니다.")
    void changeAttendanceTest() {
        DayOfMonth dayOfMonth = new DayOfMonth(2); // 불러올 날짜
        LocalTime changeTime = LocalTime.of(11, 0); // 바꿀 시간

        Assertions.assertEquals(new Attendance(LocalDateTime.of(2024, 12, 2, 11, 0)),
                attendances.changeAttendance(dayOfMonth, LocalDate.of(2024, 12, 6), changeTime));
    }

    @Test
    @DisplayName("올바른 출석 횟수를 세는지 확인합니다.")
    void calculatePresentTest() {
        Assertions.assertEquals(5, attendances.calculatePresent());
    }

    @Test
    @DisplayName("올바른 지각 횟수를 세는지 확인합니다.")
    void calculateLateTest() {
        Assertions.assertEquals(3, attendances.calculateLate());
    }

    @Test
    @DisplayName("올바른 결석 횟수를 세는지 확인합니다.")
    void calculateAbsentTest() {
        Assertions.assertEquals(2, attendances.calculateAbsent());
    }

    @Test
    @DisplayName("올바른 출석 제적 상태를 계산하는지 확인합니다.")
    void calculateAlertTest() {
        Assertions.assertEquals(AlertCode.COUNSELING, attendances.calucateAlertCode());
    }
}
