package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import domain.datetime.CampusTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampusTimeTest {

    @Test
    void 문자열_HH_COLON_MM_형식을_입력받아_객체를_생성한다() {
        // given
        String givenData = "10:30";

        // when
        CampusTime campusTime = CampusTime.from(givenData);

        // then
        assertThat(campusTime.getHour()).isEqualTo(10);
        assertThat(campusTime.getMinute()).isEqualTo(30);
    }

    @Test
    void 시간과_분을_입력받아_객체를_생성한다() {
        // given
        int hour = 12;
        int minute = 30;

        // when
        CampusTime campusTime = CampusTime.of(hour, minute);

        // then
        assertThat(campusTime.getHour()).isEqualTo(12);
        assertThat(campusTime.getMinute()).isEqualTo(30);
    }

    @Test
    void 시_및_분의_숫자가_두자리가_아닌경우_예외를_발생시킨다() {
        // given
        String givenData = "1:30";

        // when // then
        assertThatThrownBy(() -> CampusTime.from(givenData))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
    }

    @Test
    void 시간_및_분_범위를_초과하는_숫자_입력시_예외를_발생시킨다() {
        // given
        String givenData1 = "24:01";
        String givenData2 = "12:61";

        // when // then
        assertThatThrownBy(() -> CampusTime.from(givenData1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
        assertThatThrownBy(() -> CampusTime.from(givenData2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
    }

    @Test
    void 숫자_생성시_시간_및_분_범위를_초과하는_숫자_입력시_예외를_발생시킨다() {
        // given
        int hour1 = 24;
        int minute1 = 30;
        int hour2 = 12;
        int minute2 = 61;

        // when // then
        assertThatThrownBy(() -> CampusTime.of(hour1, minute1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
        assertThatThrownBy(() -> CampusTime.of(hour2, minute2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
    }

    @ParameterizedTest
    @CsvSource({
            "1a:03",
            "12:ㅇㅇ",
            "ab:4d"
    })
    void 시간_및_분으로_숫자가_아닌_값이_들어오는_경우_예외를_발생시킨다(String inputTime) {
        // when // then
        assertThatThrownBy(() -> CampusTime.from(inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
    }

    @ParameterizedTest
    @CsvSource({
            "12::03",
            "03;24",
    })
    void 구분자가_콜론이_아니거나_두개이상_들어오는_경우_예외를_발생시킨다(String inputTime) {
        // when // then
        assertThatThrownBy(() -> CampusTime.from(inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
    }

    @Test
    void 다른_캠퍼스시간_이전의_시간인지_확인한다() {
        // given
        CampusTime campusTime1 = CampusTime.from("10:30");
        CampusTime campusTime2 = CampusTime.from("10:31");
        CampusTime campusTime3 = CampusTime.from("10:32");

        // when
        boolean result1 = campusTime1.isBefore(campusTime2);
        boolean result2 = campusTime3.isBefore(campusTime2);

        // then
        assertThat(result1).isTrue();
        assertThat(result2).isFalse();
    }

    @Test
    void 다른_캠퍼스시간_이후의_시간인지_확인한다() {
        // given
        CampusTime campusTime1 = CampusTime.from("10:30");
        CampusTime campusTime2 = CampusTime.from("10:31");
        CampusTime campusTime3 = CampusTime.from("10:32");

        // when
        boolean result1 = campusTime1.isAfter(campusTime2);
        boolean result2 = campusTime3.isAfter(campusTime2);

        // then
        assertThat(result1).isFalse();
        assertThat(result2).isTrue();
    }

    @Test
    void 캠퍼스_시간_이전의_시간일_시_예외를_발생한다() {
        // given
        String inputTime = "23:01";

        // when // then
        assertThatThrownBy(() -> CampusTime.from(inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }

    @Test
    void 캠퍼스_시간_이후의_시간일_시_예외를_발생한다() {
        // given
        String inputTime = "07:59";

        // when // then
        assertThatThrownBy(() -> CampusTime.from(inputTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 캠퍼스 운영 시간은 08:00 ~ 23:00 입니다.");
    }

}
