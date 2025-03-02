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
        // when
        WarningStatus expectedValue = WarningStatus.WARN;
        WarningStatus actualValue = WarningStatus.getStatus(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("누적 결석 횟수가 3회 이상일 경우 면담을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,3", "4,2", "9,0"})
    void counselStatusTest(int tardyCount, int absentCount) {
        // when
        WarningStatus expectedValue = WarningStatus.COUNSEL;
        WarningStatus actualValue = WarningStatus.getStatus(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("누적 결석 횟수가 6회 이상일 경우 제적을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,6", "3,5", "18,0"})
    void expelStatusTest(int tardyCount, int absentCount) {
        // when
        WarningStatus expectedValue = WarningStatus.EXPEL;
        WarningStatus actualValue = WarningStatus.getStatus(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("지각 3회를 결석 1회로 환산해 기존 결석 횟수에 더한 값을 반환한다.")
    @ParameterizedTest
    @CsvSource({"6,2,4", "5,1,2", "15,0,5"})
    void convertTardiesToAbsencesTest(int tardyCount, int absentCount, int expectedValue) {
        // when
        int actualValue = WarningStatus.convertTardiesToAbsences(tardyCount, absentCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }

    @DisplayName("지각 3회를 결석으로 환산한 것을 제외한 나머지 지각 횟수를 반환한다.")
    @ParameterizedTest
    @CsvSource({"3,0", "4,1", "8,2"})
    void getTardiesAfterConversionTest(int tardyCount, int expectedValue) {
        // when
        int actualValue = WarningStatus.getTardiesAfterConversion(tardyCount);

        // then
        assertThat(actualValue).isEqualTo(expectedValue);
    }
}
