package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class DateProviderTest {

    @DisplayName("년, 월, 일을 받아서 날짜를 생성할 수 있다.")
    @Test
    void createLocalDate() {
        //given

        //when
        DateProvider actual = creatLocalDate();

        //then
        assertThat(actual).isEqualTo(DateProvider.from(2024, 12, 24));
    }

    @DisplayName("잘못된 날짜 형식이라면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {
            "2024:13:24",
            "2024:12:45",
    }, delimiter = ':')
    void invalidFormat(int year, int month, int today) {
        //given

        //when //then
        assertThatThrownBy(() -> DateProvider.from(year, month, today))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜 형식이 잘못 되었습니다.");
    }

    @DisplayName("오늘 일자를 반환한다.")
    @Test
    void getToday() {
        //given
        DateProvider dateProvider = creatLocalDate();

        //when
        int actual = dateProvider.getToday();

        //then
        assertThat(actual).isEqualTo(24);
    }

    @DisplayName("오늘 일자의 달을 반환한다.")
    @Test
    void getTodayMonth() {
        //given
        DateProvider dateProvider = creatLocalDate();

        //when
        int actual = dateProvider.getTodayMonth();

        //then
        assertThat(actual).isEqualTo(12);
    }

    @DisplayName("해당 요일에 해당하는 Calender 값을 반환한다.")
    @Test
    void getDayOfWeek() {
        //given
        DateProvider dateProvider = creatLocalDate();

        //when
        Calender actual = dateProvider.getDayOfWeek();

        //then
        assertThat(actual).isEqualTo(Calender.TUE);
    }

    private DateProvider creatLocalDate() {

        final int year = 2024;
        final int month = 12;
        final int today = 24;

        return DateProvider.from(year, month, today);
    }
}
