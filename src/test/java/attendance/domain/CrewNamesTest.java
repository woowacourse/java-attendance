package attendance.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class CrewNamesTest {
    private final CrewNames crewNames = new CrewNames();

    @BeforeEach
    void setUp() {
        crewNames.initializeCrewNames(Set.of("쿠키", "빙봉", "이든", "짱수", "빙티"));
    }

    @DisplayName("예외: 크루 명단에 없는 잘못된 크루 이름을 입력한 경우 예외 발생")
    @ParameterizedTest
    @ValueSource(strings = {"초코", "", "apple"})
    void causeException(String crewNameInput) {
        assertThatThrownBy(() -> {
            crewNames.findCrewName(crewNameInput);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 크루 명단에 존재하는 크루 이름을 입력한 경우 정상 처리")
    @ParameterizedTest
    @ValueSource(strings = {"쿠키", "빙봉", "이든", "짱수", "빙티"})
    void successExecution(String crewNameInput) {
        assertThatCode(() -> crewNames.findCrewName(crewNameInput)).doesNotThrowAnyException();
    }
}
