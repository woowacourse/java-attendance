package view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FeatureOptionTest {
    @Test
    void functionTest1() {
        String input = "1";
        FeatureOption featureOption = FeatureOption.getFunction(input);
        Assertions.assertThat(featureOption).isEqualTo(FeatureOption.APPLY_ATTENDANCE);
    }

    @Test
    void functionTest2() {
        String input = "Q";
        FeatureOption featureOption = FeatureOption.getFunction(input);
        Assertions.assertThat(featureOption).isEqualTo(FeatureOption.QUIT);
    }

    @Test
    void functionTest3() {
        String input = "7";
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> FeatureOption.getFunction(input));
    }
}
