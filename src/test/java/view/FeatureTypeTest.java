package view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FeatureTypeTest {
    @Test
    void functionTest1() {
        String input = "1";
        FeatureType featureType = FeatureType.getFunction(input);
        Assertions.assertThat(featureType).isEqualTo(FeatureType.APPLY_ATTENDANCE);
    }

    @Test
    void functionTest2() {
        String input = "Q";
        FeatureType featureType = FeatureType.getFunction(input);
        Assertions.assertThat(featureType).isEqualTo(FeatureType.QUIT);
    }

    @Test
    void functionTest3() {
        String input = "7";
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> FeatureType.getFunction(input));
    }
}
