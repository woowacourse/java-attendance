package view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class OptionTest {
    @Test
    void functionTest1() {
        String input = "1";
        Option option = Option.getFunction(input);
        Assertions.assertThat(option).isEqualTo(Option.APPLY_ATTENDANCE);
    }

    @Test
    void functionTest2() {
        String input = "Q";
        Option option = Option.getFunction(input);
        Assertions.assertThat(option).isEqualTo(Option.QUIT);
    }

    @Test
    void functionTest3() {
        String input = "7";
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> Option.getFunction(input));
    }
}
