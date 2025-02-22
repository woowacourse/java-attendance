package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ClassTimeTest {


    @Nested
    @DisplayName("성공 테스트")
    class Success {
        @ParameterizedTest
        @MethodSource
        @DisplayName("요일에 맞는 강의 시간을 가져온다.")
        void findClassTimeByDateTimeTest(final LocalDate date, final LocalTime time) {
            //should
            assertThat(ClassTime.findClassTimeByDateTime(date).getStartTime()).isEqualTo(time);

        }

        private static Stream<Arguments> findClassTimeByDateTimeTest() {
            return Stream.of(
                    Arguments.of(LocalDate.of(2024, 12, 16), LocalTime.of(13, 0)),
                    Arguments.of(LocalDate.of(2024, 12, 17), LocalTime.of(10, 0)),
                    Arguments.of(LocalDate.of(2024, 12, 18), LocalTime.of(10, 0)),
                    Arguments.of(LocalDate.of(2024, 12, 19), LocalTime.of(10, 0)),
                    Arguments.of(LocalDate.of(2024, 12, 20), LocalTime.of(10, 0))
            );
        }
    }


    @Nested
    @DisplayName("실패 테스트")
    class Failure {
        @ParameterizedTest
        @MethodSource
        @DisplayName("주말과 공휴일에 강의 시간을 조회하여 예외가 발생한다.")
        void findClassTimeByDateTimeTest(final LocalDate date) {
            //should
            assertThatIllegalArgumentException().isThrownBy(() -> ClassTime.findClassTimeByDateTime(date));

        }

        private static Stream<LocalDate> findClassTimeByDateTimeTest() {
            return Stream.of(
                    LocalDate.of(2024,12, 14),
                    LocalDate.of(2024, 12, 15),
                    LocalDate.of(2024, 12, 25)
            );
        }
    }

}
