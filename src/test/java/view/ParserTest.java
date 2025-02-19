package view;

import static org.junit.jupiter.api.Assertions.*;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ParserTest {
    @ParameterizedTest
    @ValueSource(strings = {"1", "12", "1:2:3"})
    void parserTest1(String value) {
        Assertions.assertThatIllegalArgumentException().isThrownBy(() -> Parser.parseAttendanceTime(value));
    }

    @ParameterizedTest
    @ValueSource(strings = {"11:30", "12:45", "23:30"})
    void parserTest2(String value) {
        Assertions.assertThatNoException().isThrownBy(() -> Parser.parseAttendanceTime(value));
    }
}