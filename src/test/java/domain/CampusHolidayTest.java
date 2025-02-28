package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class CampusHolidayTest {

    @ParameterizedTest
    @MethodSource
    @DisplayName("주말 및 공휴일인지 확인 한다.")
    void test1(final LocalDate localDate, final boolean expected) {
        //should
        assertThat(CampusHoliday.isDayOff(localDate)).isEqualTo(expected);

    }

    private static Stream<Arguments> test1() {
        return Stream.of(
                Arguments.of(LocalDate.of(2024, 12, 13), false),
                Arguments.of(LocalDate.of(2024, 12, 14), true),
                Arguments.of(LocalDate.of(2024, 12, 15), true),
                Arguments.of(LocalDate.of(2024, 12, 25), true)
        );
    }


}
