import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class AttendanceTimeCheckerTest {

    private static Stream<Arguments> testCasesForCheckOperationTime() {
        return Stream.of(
                Arguments.of(7, 0),
                Arguments.of(6, 0),
                Arguments.of(7, 58),
                Arguments.of(23, 1),
                Arguments.of(23, 30)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForCheckOperationTime")
    @DisplayName("캠퍼스 운영 시간이 아니면 출석할 수 없다")
    void test1(int hour, int minute) {
        // given
        LocalTime requestedTime = LocalTime.of(hour, minute);

        // when & then
        assertThatThrownBy(() -> AttendanceTimeChecker.checkTime(requestedTime))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("주말인 경우 출석할 수 없다.")
    void test2() {
        // given
        LocalDate requestedDate = LocalDate.of(2024, 12, 1);

        // when & then
        assertThatThrownBy(() -> AttendanceTimeChecker.checkDate(requestedDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("공휴일인 경우 출석할 수 없다.")
    void test3() {
        // given
        LocalDate requestedDate = LocalDate.of(2024, 12, 25);

        // when & then
        assertThatThrownBy(() -> AttendanceTimeChecker.checkDate(requestedDate))
                .isInstanceOf(IllegalArgumentException.class);
    }

    private static Stream<Arguments> testCasesForTestIsAttendanceRequiredDate() {
        return Stream.of(
                Arguments.of(2, true),
                Arguments.of(25, false),
                Arguments.of(8, false),
                Arguments.of(27, true)
        );
    }

    @ParameterizedTest
    @MethodSource("testCasesForTestIsAttendanceRequiredDate")
    @DisplayName("출석이 필요한 날짜를 확인한다.")
    void test(int day, boolean expected) {
        // given
        LocalDate requestedDate = LocalDate.of(2024, 12, day);

        // when
        boolean actual = AttendanceTimeChecker.isAttendanceRequiredDate(requestedDate);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}

