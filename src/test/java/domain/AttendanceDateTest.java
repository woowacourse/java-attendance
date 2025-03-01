package domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceDateTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("등교일이 아니므로 예외가 발생한다.")
    void test1(final LocalDate localDate) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceDate(localDate));

    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 25)),
                Arguments.of(LocalDate.of(2024, 12, 21)),
                Arguments.of(LocalDate.of(2024, 12, 22))
        );
    }

}
