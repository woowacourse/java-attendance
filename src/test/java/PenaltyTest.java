import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Penalty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class PenaltyTest {

    @DisplayName("결석 2회 이상 3회 미만에 해당하는 경우 경고를 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,2", "3,1", "6,0"})
    void should_ReturnWarning_When_PenaltyApplies(int latenessCount, int absenceCount) {
        Penalty penalty = Penalty.from(latenessCount, absenceCount);

        assertThat(penalty).isSameAs(Penalty.WARNING);
    }

    @DisplayName("결석 3회 이상 6회 미만에 해당하는 경우 면담을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,3", "0,4", "0,5", "3,2", "6,1", "8,3"})
    void should_ReturnCounsel_When_PenaltyApplies(int latenessCount, int absenceCount) {
        Penalty penalty = Penalty.from(latenessCount, absenceCount);

        assertThat(penalty).isSameAs(Penalty.COUNSEL);
    }

    @DisplayName("결석 6회 이상에 해당하는 경우 제적을 반환한다.")
    @ParameterizedTest
    @CsvSource({"0,6", "12,2", "15,1"})
    void should_ReturnExpulsion_When_PenaltyApplies(int latenessCount, int absenceCount) {
        Penalty penalty = Penalty.from(latenessCount, absenceCount);

        assertThat(penalty).isSameAs(Penalty.EXPULSION);
    }
}
