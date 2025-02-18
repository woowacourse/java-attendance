package domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CrewTest {
    @Test
    @DisplayName("이름이 NULL이면 예외 발생")
    void nullNameThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> Crew.of(null)).withMessage("[ERROR]");
    }

    @Test
    @DisplayName("이름이 공백이면 예외 발생")
    void blankNameThrowException() {
        assertThatIllegalArgumentException().isThrownBy(() -> Crew.of(" ")).withMessage("[ERROR]");
    }

    @Test
    @DisplayName("이름이 두글자 미만이면 예외 발생")
    void underNameLengthTwoThrowException() {
        String name = "한";
        assertThatIllegalArgumentException().isThrownBy(() -> Crew.of(name)).withMessage("[ERROR]");
    }

    @Test
    @DisplayName("이름이 네글자가 넘으면 예외 발생")
    void overNameLengthFourThrowException() {
        String name = "한백과사전";
        assertThatIllegalArgumentException().isThrownBy(() -> Crew.of(name)).withMessage("[ERROR]");
    }

    @Test
    @DisplayName("2글자에서 4글자 사이의 이름 충족시 정상적으로 생성")
    void createCrewTest() {
        String name = "조로";
        assertThatThrownBy(() -> Crew.of(name))
                .doesNotThrowAnyException();
    }
}
