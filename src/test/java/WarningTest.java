import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class WarningTest {
    @DisplayName("지각 3회당 결석 1회로 전환해 결석 횟수를 계산한다.")
    @Test
    void warningTest() {
        WarningCounter warningCounter = new WarningCounter(2, 6);
        assertThat(warningCounter.getConvertedAbsences()).isEqualTo(4);
    }
}
