package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    private final Attendances attendances = new Attendances();

    void makeTestAttendances() {
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 2, 13, 31)));
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 3, 10, 10)));
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 4, 10, 0)));
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 5, 10, 0)));
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 6, 10, 0)));
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 9, 13, 0)));
        attendances.addAttendance(new Attendance(LocalDateTime.of(2024, 12, 10, 13, 0)));
    }

    @BeforeEach
    void make() {
        makeTestAttendances();
    }

    @DisplayName("현재 몇 회의 출석을 했는지 확인합니다.")
    @Test
    void calculatePresentTest() {
        Assertions.assertEquals(4, attendances.countPresent());
    }

    @DisplayName("현재 몇 회의 결석을 했는지 확인합니다.")
    @Test
    void calculateLateTest() {
        Assertions.assertEquals(1, attendances.countLate());
    }

    @DisplayName("현재 몇 회의 지각을 했는지 확인합니다.")
    @Test
    void calculateAbsentTest() {
        Assertions.assertEquals(2, attendances.countAbsent());
    }

    @DisplayName("특정 날짜의 출석이 존재하는지 확인합니다.")
    @Test
    void checkAlreadyExistTest() {
        LocalDate testDay = LocalDate.of(2024, 12, 3);
        Assertions.assertNotNull(attendances.getSpecificAttendance(testDay));
    }

    @DisplayName("현재 출석 상태가 어떤 제적 상태인지 확인합니다.")
    @Test
    void calculateAttendanceAlertLevel() {
        Assertions.assertEquals(attendances.calculateAttendanceAlertLevel(),
                AttendanceAlertLevel.CAUTION);
    }
}