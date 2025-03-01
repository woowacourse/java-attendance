package util;

import fixture.LocalDateFixture;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class DateTimeUtilTest {

    @Nested
    @DisplayName("예외가 발생하지 않는 테스트")
    class Success {
        private static Stream<Arguments> convertToLocalTimeCases() {
            return Stream.of(
                    Arguments.of("00:00", LocalTime.of(0, 0)),
                    Arguments.of("09:59", LocalTime.of(9, 59)),
                    Arguments.of("23:59", LocalTime.of(23, 59))
            );
        }

        private static Stream<Arguments> convertDayCases() {
            return Stream.of(
                    Arguments.of(LocalDate.of(2025, 2, 25), 1),
                    Arguments.of(LocalDate.of(2025, 2, 25), 2),
                    Arguments.of(LocalDate.of(2025, 2, 25), 3)
            );
        }


        @Test
        @DisplayName("해당 날짜가 주말이면 true를 반환한다.")
        void isWeekend_test_true() {
            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.SATURDAY)).isTrue();
                Assertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.SUNDAY)).isTrue();
            });
        }

        @Test
        @DisplayName("해당 날짜가 주말이 아니면 false를 반환한다.")
        void isWeekend_test_false() {
            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.MONDAY)).isFalse();
                softAssertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.TUESDAY)).isFalse();
                softAssertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.WEDNESDAY)).isFalse();
                softAssertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.THURSDAY)).isFalse();
                softAssertions.assertThat(DateTimeUtil.isWeekend(LocalDateFixture.FRIDAY)).isFalse();
            });
        }

        @Test
        @DisplayName("시간(시분)과 시간 사이를 구분할 수 있다")
        void isInRange_test() {
            // given
            LocalTime startTime = LocalTime.of(1, 0);
            LocalTime endTime = LocalTime.of(2, 31);

            LocalTime earlyTime = LocalTime.of(0, 59);
            LocalTime betweenTime = LocalTime.of(2, 30);
            LocalTime overTime = LocalTime.of(2, 32);

            // when & then
            SoftAssertions.assertSoftly(softAssertions -> {
                softAssertions.assertThat(DateTimeUtil.isInRange(startTime, endTime, earlyTime)).isFalse();
                softAssertions.assertThat(DateTimeUtil.isInRange(startTime, endTime, betweenTime)).isTrue();
                softAssertions.assertThat(DateTimeUtil.isInRange(startTime, endTime, overTime)).isFalse();
            });
        }

        @ParameterizedTest
        @MethodSource(value = "convertToLocalTimeCases")
        @DisplayName("문자열을 LocalTime 타입으로 변환한다")
        void convertToLocalTime_parse_test(String time, LocalTime converted) {
            // when & then
            Assertions.assertThat(DateTimeUtil.convertToLocalTime(time))
                    .isEqualTo(converted);
        }

        @ParameterizedTest
        @MethodSource(value = "convertDayCases")
        @DisplayName("LocalDate을 원하는 날짜(일)로 변환한다")
        void convertDay_parse_test(LocalDate date, int day) {
            // when & then
            Assertions.assertThat(DateTimeUtil.convertDay(date, day))
                    .isEqualTo(date.withDayOfMonth(day));
        }
    }

    @Nested
    @DisplayName("예외 테스트")
    class Fail {

        private static Stream<Arguments> convertToLocalTimeCases() {
            return Stream.of(
                    Arguments.of("0:0"),
                    Arguments.of("09시59분"),
                    Arguments.of("2359")
            );
        }

        private static Stream<Arguments> convertDayCases() {
            return Stream.of(
                    Arguments.of(LocalDate.of(2025, 2, 25), 32),
                    Arguments.of(LocalDate.of(2025, 2, 25), 0),
                    Arguments.of(LocalDate.of(2025, 2, 25), -1)
            );
        }

        @Test
        @DisplayName("시작 시간이 종료 시간보다 뒤인 경우 예외가 발생한다")
        void isInRange_test_exception() {
            // given
            LocalTime startTime = LocalTime.of(3, 0);
            LocalTime endTime = LocalTime.of(2, 31);

            LocalTime betweenTime = LocalTime.of(2, 30);

            // when & then
            Assertions.assertThatThrownBy(() -> {
                DateTimeUtil.isInRange(startTime, endTime, betweenTime);
            }).isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource(value = "convertToLocalTimeCases")
        @DisplayName("LocalTime 타입으로 변환할 수 없는 문자열이 들어오면 예외가 발생한다")
        void convertToLocalTime_parse_test_exception(String time) {
            // when & then
            Assertions.assertThatThrownBy(() -> DateTimeUtil.convertToLocalTime(time))
                    .isInstanceOf(IllegalArgumentException.class);
        }

        @ParameterizedTest
        @MethodSource(value = "convertDayCases")
        @DisplayName("변환하려는 날짜(일)이 존재하는 날짜가 아니면 예외가 발생한다")
        void convertDay_parse_test_exception(LocalDate date, int day) {
            // when & then
            Assertions.assertThatThrownBy(() -> DateTimeUtil.convertDay(date, day))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}