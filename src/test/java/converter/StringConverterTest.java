package converter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import model.Attendance;
import model.Crew;
import org.junit.jupiter.api.Test;

class StringConverterTest {

    @Test
    void 크루와_시간을_전달하면_오늘의_출석으로_변환한다() {
        //given
        String nickname = "쿠키";
        StringConverter stringConverter = new StringConverter();
        Crew crew = Crew.of(nickname);
        LocalDate today = LocalDate.of(2024, 12, 2);
        LocalDateTime time = LocalDateTime.of(today, LocalTime.of(9, 44));
        Attendance expected = Attendance.of(crew, time);

        //when
        Attendance actual = stringConverter.convertToAttendance(crew, "09:44", today);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 시간_문자열을_LocalDateTime형식으로_변환한다() {
        //given
        StringConverter stringConverter = new StringConverter();
        String rawTime = "2025-02-20 09:07";
        LocalDateTime expected = LocalDateTime.of(2025, 2, 20, 9, 7);
        //when
        LocalDateTime actual = stringConverter.convertToLocalDateTime(rawTime);
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 시간_문자열이_공백일_경우_예외를_발생시킨다() {
        //given
        StringConverter stringConverter = new StringConverter();
        String rawTime = "";
        //when & then
        assertThatThrownBy(() -> stringConverter.convertToLocalDateTime(rawTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("입력은 null이거나 공백일 수 없습니다.");
    }

    @Test
    void 시간_문자열이_잘못된_형식일_경우_예외를_발생시킨다() {
        //given
        StringConverter stringConverter = new StringConverter();
        String rawTime = "2025-02-20-09-07";
        //when & then
        assertThatThrownBy(() -> stringConverter.convertToLocalDateTime(rawTime))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("시간 형식이 올바르지 않습니다.");
    }

    @Test
    void 일자와_시간을_LocalDateTime객체로_변환한다() {
        //given
        StringConverter stringConverter = new StringConverter();
        String rawDate = "3";
        String rawTime = "09:30";
        LocalDateTime expected = LocalDateTime.of(2024, 12, 3, 9, 30);
        //when
        LocalDateTime actual = stringConverter.convertToLocalDateTime(rawDate, rawTime, LocalDate.of(2024, 12, 3));
        //then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 잘못된_날짜형식일_경우_예외를_발생시킨다() {
        //given
        StringConverter converter = new StringConverter();
        String rawDate = "a";
        String rawTime = "09:30";
        //when & then
        assertThatThrownBy(() -> converter.convertToLocalDateTime(rawDate, rawTime, LocalDate.of(2024, 12, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("날짜 형식이 아닙니다.");
    }

    @Test
    void 존재하지_않는_날짜일_경우_예외를_발생시킨다() {
        //given
        StringConverter converter = new StringConverter();
        String rawDate = "32";
        String rawTime = "09:30";
        //when & then
        assertThatThrownBy(() -> converter.convertToLocalDateTime(rawDate, rawTime, LocalDate.of(2024, 12, 3)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("잘못된 날짜입니다.");
    }
}
