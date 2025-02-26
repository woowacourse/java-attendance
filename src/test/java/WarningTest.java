import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class WarningTest {
    @DisplayName("지각 3회당 결석 1회로 전환해 결석 횟수를 계산한다.")
    @Test
    void warningTest() {
        WarningCounter warningCounter = new WarningCounter(2, 6);
        assertThat(warningCounter.getConvertedAbsences()).isEqualTo(4);
    }

    @DisplayName("누적 결석 횟수가 2회 이상일 경우 경고를 반환한다.")
    @Test
    void warningStatusTest() {
        WarningCounter warningCounter = new WarningCounter(2, 0);
        assertThat(warningCounter.getStatus()).isEqualTo("경고");
    }

    @DisplayName("누적 결석 횟수가 3회 이상일 경우 면담을 반환한다.")
    @Test
    void counselingStatusTest() {
        WarningCounter warningCounter = new WarningCounter(3, 0);
        assertThat(warningCounter.getStatus()).isEqualTo("면담");
    }

    @DisplayName("누적 결석 횟수가 6회 이상일 경우 제적을 반환한다.")
    @Test
    void expelledStatusTest() {
        WarningCounter warningCounter = new WarningCounter(6, 0);
        assertThat(warningCounter.getStatus()).isEqualTo("제적");
    }
}
