package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatisticTest {
    @DisplayName("경고 대상자 상태 값을 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"0, 2", "3, 1", "4, 1", "5, 1", "6, 0", "7, 0", "8, 0"})
    void test1(int late, int absence) {
        // given
        AttendanceStatistic statistic = new AttendanceStatistic(Map.of(
                AttendanceStatus.LATE, late,
                AttendanceStatus.ABSENCE, absence
        ));

        // when
        ExpulsionRiskStatus status = statistic.getExpulsionRiskStatus();

        // then
        assertThat(status).isSameAs(ExpulsionRiskStatus.WARNING);
    }

    @DisplayName("면담 대상자 상태 값을 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "0, 3", "1, 3", "2, 3", "3, 2", "4, 2", "5, 2", "6, 1", "7, 1", "8, 1", "9, 0", // 결석 3회
            "0, 4", "3, 3", "4, 3", "5, 3", "6, 2", "7, 2", "8, 2", "9, 1", "10, 1", "11, 1", "12, 0", // 결석 4회
            "0, 5", "3, 4", "6, 3", "9, 2", "12, 1", "15, 0" // 결석 5회
    })
    void test2(int late, int absence) {
        // given
        AttendanceStatistic statistic = new AttendanceStatistic(Map.of(
                AttendanceStatus.LATE, late,
                AttendanceStatus.ABSENCE, absence
        ));

        // when
        ExpulsionRiskStatus status = statistic.getExpulsionRiskStatus();

        // then
        assertThat(status).isSameAs(ExpulsionRiskStatus.INTERVIEW);
    }

    @DisplayName("제적 대상자 상태 값을 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {"0, 6", "1, 6", "2, 6", "3, 6"})
    void test3(int late, int absence) {
        // given
        AttendanceStatistic statistic = new AttendanceStatistic(Map.of(
                AttendanceStatus.LATE, late,
                AttendanceStatus.ABSENCE, absence
        ));

        // when
        ExpulsionRiskStatus status = statistic.getExpulsionRiskStatus();

        // then
        assertThat(status).isSameAs(ExpulsionRiskStatus.EXPELLED);
    }

    @DisplayName("정상 상태 값을 올바르게 반환할 수 있다.")
    @ParameterizedTest
    @CsvSource(value = {
            "0, 0", "1, 0", "2, 0", // 결석 0회
            "0, 1", "1, 1", "2, 1", "3, 0", "4, 0", "5, 0", // 결석 1회
    })
    void test4(int late, int absence) {
        // given
        AttendanceStatistic statistic = new AttendanceStatistic(Map.of(
                AttendanceStatus.LATE, late,
                AttendanceStatus.ABSENCE, absence
        ));

        // when
        ExpulsionRiskStatus status = statistic.getExpulsionRiskStatus();

        // then
        assertThat(status).isSameAs(ExpulsionRiskStatus.NORMAL);
    }
}
