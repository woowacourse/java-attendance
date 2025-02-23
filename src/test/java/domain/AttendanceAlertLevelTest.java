package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceAlertLevelTest {
    @DisplayName("")
    @Test
    void calculateAttendanceAlertLevelTest() {
        int testAbsent = 0;
        int testLate = 10;

        Assertions.assertEquals(AttendanceAlertLevel.COUNSEL_REQUIRED,
                AttendanceAlertLevel.calculateAttendanceAlertLevel(testAbsent, testLate));
    }

}