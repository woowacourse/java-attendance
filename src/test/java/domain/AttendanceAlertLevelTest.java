package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AttendanceAlertLevelTest {
    @DisplayName("결석 수가 6회 이상인 경우 제적입니다.")
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 9})
    void dismissedTest(int absentTotal) {
        Assertions.assertEquals(AttendanceAlertLevel.DISMISSED, AttendanceAlertLevel.calculateAttendanceAlertLevel(absentTotal));
    }

    @DisplayName("결석 수가 3회 이상 6회 미만인 경우 면담입니다.")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5})
    void counselRequiredTest(int absentTotal) {
        Assertions.assertEquals(AttendanceAlertLevel.COUNSEL_REQUIRED, AttendanceAlertLevel.calculateAttendanceAlertLevel(absentTotal));
    }

    @DisplayName("결석 수가 2회 이상 3회 미만인 경우 경고입니다.")
    @Test
    void cautionTest() {
        Assertions.assertEquals(AttendanceAlertLevel.CAUTION, AttendanceAlertLevel.calculateAttendanceAlertLevel(2));
    }

    @DisplayName("결석 수가 2회 미만인 경우 일반입니다.")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void normalTest(int absentTotal) {
        Assertions.assertEquals(AttendanceAlertLevel.NORMAL, AttendanceAlertLevel.calculateAttendanceAlertLevel(absentTotal));
    }
}