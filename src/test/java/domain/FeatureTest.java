package domain;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class FeatureTest {

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "Q"})
    @DisplayName("제공되는 기능을 확인한다.")
    void checkProvided(String input) {
        assertThatNoException().isThrownBy(() -> Feature.isProvided(input));
    }

    @ParameterizedTest
    @ValueSource(strings = {"6", "ok", "go", "y"})
    @DisplayName("제공되지 않는 기능을 확인한다.")
    void checkNotProvided(String input) {
        assertThatThrownBy(() -> Feature.isProvided(input));
    }
}