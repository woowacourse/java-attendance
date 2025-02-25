package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CalenderTest {

    @DisplayName("특정 날짜의 요일을 반환한다.")
    @Test
    void getDayOfWeekByDayOfMonth() {
        //given
        DayOfWeek thursday = DayOfWeek.THURSDAY;

        //when
        Calender result = Calender.findBy(thursday);

        //then
        assertThat(result.getDescription()).isEqualTo("목요일");
    }

    @DisplayName("날짜가 공휴일이라면 true를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideHolyDay")
    void isHolyDay(LocalDate dayOfWeek) {
        //given

        //when
        boolean actual = Calender.isHolyDay(dayOfWeek);

        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("날짜가 공휴일이 아니라면 false를 반환한다.")
    @ParameterizedTest
    @MethodSource("provideLocalDate")
    void isNotHolyDay(LocalDate localDate) {
        //given

        //when
        boolean actual = Calender.isHolyDay(localDate);

        //then
        assertThat(actual).isFalse();
    }


    @DisplayName("공휴일에 출석확인을 하면 예외가 발생한다.")
    @Test
    void validateHolyDay() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        //when & then
        assertThatThrownBy(() -> Calender.validateHolyDay(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("공휴일에는 출석을 할 수 없습니다.");

    }

    @DisplayName("공휴일이 아닌 날에 출석확인을 할 수 있다.")
    @ParameterizedTest
    @MethodSource("provideLocalDate")
    void holyDay(LocalDate dayOfWeek) {

        //when & then
        assertThatCode(() -> Calender.validateHolyDay(dayOfWeek))
                .doesNotThrowAnyException();
    }

    static Stream<LocalDate> provideLocalDate() {
        return Stream.of(
                LocalDate.of(2024, 12, 2),
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 4),
                LocalDate.of(2024, 12, 5),
                LocalDate.of(2024, 12, 6),
                LocalDate.of(2024, 12, 9),
                LocalDate.of(2024, 12, 10),
                LocalDate.of(2024, 12, 12),
                LocalDate.of(2024, 12, 13),
                LocalDate.of(2024, 12, 16),
                LocalDate.of(2024, 12, 18),
                LocalDate.of(2024, 12, 19),
                LocalDate.of(2024, 12, 20),
                LocalDate.of(2024, 12, 23),
                LocalDate.of(2024, 12, 24),
                LocalDate.of(2024, 12, 26),
                LocalDate.of(2024, 12, 27),
                LocalDate.of(2024, 12, 30),
                LocalDate.of(2024, 12, 31))
                ;
    }


    static Stream<LocalDate> provideHolyDay() {
        return Stream.of(
                LocalDate.of(2024, 12, 1),
                LocalDate.of(2024, 12, 7),
                LocalDate.of(2024, 12, 8),
                LocalDate.of(2024, 12, 14),
                LocalDate.of(2024, 12, 15),
                LocalDate.of(2024, 12, 21),
                LocalDate.of(2024, 12, 22),
                LocalDate.of(2024, 12, 25),
                LocalDate.of(2024, 12, 28),
                LocalDate.of(2024, 12, 29))
                ;
    }
}
