package domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewStatusTest {
    @DisplayName("결석이 2회 미만인 경우 정상이다")
    @ParameterizedTest
    @ValueSource(ints = {0, 1})
    void normalTest(int absentTotal) {
        Assertions.assertEquals(CrewStatus.NORMAL, CrewStatus.calculateCrewStatus(absentTotal));
    }

    @DisplayName("결석이 2회 이상 3회 미만인 경우 경고 대상자이다")
    @Test
    void WarningTest() {
        Assertions.assertEquals(CrewStatus.WARNING, CrewStatus.calculateCrewStatus(2));
    }

    @DisplayName("결석이 3회 이상 6회 미만인 경우 면담 대상자이다")
    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5})
    void CounselTest(int absentTotal) {
        Assertions.assertEquals(CrewStatus.COUNSEL, CrewStatus.calculateCrewStatus(absentTotal));
    }

    @DisplayName("결석이 6회 이상인 경우 제적 대상자이다")
    @ParameterizedTest
    @ValueSource(ints = {6, 7, 8})
    void expelledTest(int absentTotal) {
        Assertions.assertEquals(CrewStatus.EXPELLED, CrewStatus.calculateCrewStatus(absentTotal));
    }
}