package attendance.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WarningStatusTest {

    @DisplayName("결석 2회 미만이면 경고대상자가 아니다.")
    @ParameterizedTest
    @ValueSource(longs = {0, 1})
    void test_none(long absenceCount) {
        // given
        long lateCount = 0;

        // when
        WarningStatus status = WarningStatus.from(absenceCount, lateCount);

        // then
        Assertions.assertThat(status).isEqualTo(WarningStatus.NONE);
    }

    @DisplayName("결석 2회면 경고 대상자이다.")
    @Test
    void test_warning() {
        // given
        long lateCount = 0;
        long absenceCount = 2;

        // when
        WarningStatus status = WarningStatus.from(absenceCount, lateCount);

        // then
        Assertions.assertThat(status).isEqualTo(WarningStatus.WARNING);
    }

    @DisplayName("결석 3회 이상 6회 미만이면 경고 대상자이다.")
    @ParameterizedTest
    @ValueSource(longs = {3, 4, 5})
    void test_needMeeting(long absenceCount) {
        // given
        long lateCount = 0;

        // when
        WarningStatus status = WarningStatus.from(absenceCount, lateCount);

        // then
        Assertions.assertThat(status).isEqualTo(WarningStatus.NEED_MEETING);
    }

    @DisplayName("결석 6회 이상이면 제적 대상자이다.")
    @Test
    void test_out() {
        // given
        long lateCount = 0;
        long absenceCount = 6;

        // when
        WarningStatus status = WarningStatus.from(absenceCount, lateCount);

        // then
        Assertions.assertThat(status).isEqualTo(WarningStatus.OUT);
    }

    @DisplayName("지각 3회는 결석 1회로 하여 결석 2회면 경고 대상자이다.")
    @ParameterizedTest
    @ValueSource(longs = {6, 7, 8})
    void test_warning_withLateCount(long lateCount) {
        // given
        long absenceCount = 0;

        // when
        WarningStatus status = WarningStatus.from(absenceCount, lateCount);

        // then
        Assertions.assertThat(status).isEqualTo(WarningStatus.WARNING);
    }

}