package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceAlertLevelTest {
    @DisplayName("결석과 지각 횟수가 주어졌을때, 면담 대상인지 확인합니다.")
    @Test
    void calculateAttendanceAlertLevelCounselTest() {
        int testAbsent = 0;
        int testLate = 10;

        Assertions.assertEquals(AttendanceAlertLevel.COUNSEL_REQUIRED,
                AttendanceAlertLevel.calculateAttendanceAlertLevel(testAbsent, testLate));
    }

    @DisplayName("결석과 지각 횟수가 주어졌을때, 제적 대상인지 확인합니다.")
    @Test
    void calculateAttendanceAlertLevelDismissTest() {
        int testAbsent = 3;
        int testLate = 10;

        Assertions.assertEquals(AttendanceAlertLevel.DISMISSED,
                AttendanceAlertLevel.calculateAttendanceAlertLevel(testAbsent, testLate));
    }

    @DisplayName("결석과 지각 횟수가 주어졌을때, 면담 대상인지 확인합니다.")
    @Test
    void calculateAttendanceAlertLevelCautionTest() {
        int testAbsent = 1;
        int testLate = 3;

        Assertions.assertEquals(AttendanceAlertLevel.CAUTION,
                AttendanceAlertLevel.calculateAttendanceAlertLevel(testAbsent, testLate));
    }

}