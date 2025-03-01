package domain;

import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("캠퍼스 운영 시간이 아니므로 예외가 발생한다.")
    void test1(final LocalTime localTime) {
        //should
        assertThatIllegalArgumentException().isThrownBy(() -> new AttendanceTime(localTime));

    }

    private static Stream<LocalTime> test1() {
        return Stream.of(
                LocalTime.of(7, 59),
                LocalTime.of(23, 1)
        );
    }

    private class AttendanceTime {

        private final LocalTime localtime;

        public AttendanceTime(final LocalTime localtime) {
            this.localtime = localtime;
        }

    }
}
