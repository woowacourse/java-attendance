package view;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class FunctionTest {
    @Test
    void functionTest1() {
        String input = "1";
        Function function = Function.getFunction(input);
        Assertions.assertThat(function).isEqualTo(Function.APPLY_ATTENDANCE);
    }

    @Test
    void functionTest2() {
        String input = "Q";
        Function function = Function.getFunction(input);
        Assertions.assertThat(function).isEqualTo(Function.QUIT);
    }

    @Test
    void functionTest3() {
        String input = "7";
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> Function.getFunction(input));
    }
}
