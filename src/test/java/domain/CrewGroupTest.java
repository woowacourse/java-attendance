package domain;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewGroupTest {
    @DisplayName("중복되는 이름은 허용되지 않는다.")
    @Test
    void test() {
        List<String> crews = List.of("수양", "수양");
        Assertions.assertThatThrownBy(() -> CrewGroup.from(crews))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("");
    }
}
