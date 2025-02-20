package domain;

import domain.crew.Crews;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CrewsTest {
    @ParameterizedTest
    @ValueSource(strings = {"히스타", "히로"})
    void crewsTest1(String nickname) {
        Crews crews = new Crews();
        Assertions.assertThatIllegalArgumentException()
                .isThrownBy(() -> crews.findCrewBy(nickname));
    }
}