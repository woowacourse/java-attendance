import domain.TimeBoundary;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TimeBoundaryTest {

    private static Stream<Arguments> provideDateAndTimeBoundary() {
        return Stream.of(
                Arguments.of(LocalDate.parse("2024-12-02"),
                        new TimeBoundary(LocalTime.parse("13:00"), LocalTime.parse("13:05"), LocalTime.parse("13:30"))),
                Arguments.of(LocalDate.parse("2024-12-03"),
                        new TimeBoundary(LocalTime.parse("10:00"), LocalTime.parse("10:05"), LocalTime.parse("10:30")))
        );
    }

    @ParameterizedTest
    @MethodSource("provideDateAndTimeBoundary")
    @DisplayName("date를 기반으로 교육 시간 생성 기능")
    void createTimeBoundaryUsingDate(LocalDate date, TimeBoundary expected) {
        //when
        TimeBoundary actual = TimeBoundary.createTimeBoundary(date);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
