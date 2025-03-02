package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class WarningStatusTest {
    @DisplayName("누적 결석 횟수가 2회 이상일 경우 경고를 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,2", "3,1", "6,0"})
    void warnStatusTest(int tardyCount, int absentCount) {
        // given
        WarningStatus expectedValue = WarningStatus.WARN;

        // when
        WarningStatus actualValue = WarningStatus.getStatus(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("누적 결석 횟수가 3회 이상일 경우 면담을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,3", "4,2", "9,0"})
    void counselStatusTest(int tardyCount, int absentCount) {
        // given
        WarningStatus expectedValue = WarningStatus.COUNSEL;

        // when
        WarningStatus actualValue = WarningStatus.getStatus(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("누적 결석 횟수가 6회 이상일 경우 제적을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,6", "3,5", "18,0"})
    void expelStatusTest(int tardyCount, int absentCount) {
        // given
        WarningStatus expectedValue = WarningStatus.EXPEL;

        // when
        WarningStatus actualValue = WarningStatus.getStatus(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
