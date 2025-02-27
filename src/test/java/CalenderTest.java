import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CalenderTest {

    @DisplayName("날짜(일)에 해당하는 HolyDay를 반환한다.")
    @Test
    void findBy() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 14);

        //when
        Calender holyDay = Calender.findBy(localDate);

        //then
        assertThat(holyDay).isEqualTo(Calender.SAT);
    }

    @DisplayName("공휴일에 출석을 한다면 예외가 발생한다.")
    @Test
    void holyDay() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 14);

        //when //then
        assertThatThrownBy(() -> Calender.isHolyDay(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%02d월 %02d일 %s은 등교일이 아닙니다.", localDate.getMonth().getValue(), localDate.getDayOfMonth(),
                        "토요일");
    }

    @DisplayName("공휴일이 아니라면 예외가 발생하지 않는다.")
    @Test
    void notHolyDay() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 2);

        //when //then
        assertThatCode(() -> Calender.isHolyDay(localDate))
                .doesNotThrowAnyException();
    }

    @DisplayName("공휴일엔 지정 공휴일도 포함된다.")
    @Test
    void holyDay2() {
        //given
        LocalDate localDate = LocalDate.of(2024, 12, 25);

        //when //then
        assertThatThrownBy(() -> Calender.isHolyDay(localDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("%02d월 %02d일 %s은 등교일이 아닙니다.", localDate.getMonth().getValue(), localDate.getDayOfMonth(),
                        "수요일");
    }
}
