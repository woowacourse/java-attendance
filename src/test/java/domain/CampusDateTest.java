package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampusDateTest {

    @Test
    void 현재날짜를_입력받아_객체를_생성한다() {
        // given
        LocalDate now = LocalDate.of(2025, 2, 27);

        // when
        CampusDate campusDate = CampusDate.fromNow(now);

        // then
        assertThat(campusDate.getMonth()).isEqualTo(2);
        assertThat(campusDate.getDay()).isEqualTo(27);
    }

    @Test
    void 일을_입력받아_객체를_생성한다() {
        // given
        LocalDate date = LocalDate.of(2025, 2, 27);
        int day = 7;

        // when
        CampusDate campusDate = CampusDate.ofNowAndDay(date, day);

        // then
        assertThat(campusDate.getMonth()).isEqualTo(2);
        assertThat(campusDate.getDay()).isEqualTo(7);
    }

    @ParameterizedTest
    @CsvSource(value =
            {
                    "2,29",
                    "4,31",
                    "6,31",
                    "9,31",
                    "11,31",
            }
    )
    void 현재_월의_날짜범위를_넘어간경우_예외를_발생시킨다(int month, int day) {
        // given
        LocalDate now = LocalDate.of(2025, 2, 27);

        // when // then
        assertThatThrownBy(() -> CampusDate.ofNowAndDay(now.withMonth(month), day))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 해당 월의 가능한 일 수 내에서 입력해 주세요.");
    }

}
