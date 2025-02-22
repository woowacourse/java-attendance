package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TimeTest {

    @Test
    void 등교_시간_객체_반환() {
        //given
        String originTime = "09:59";
        Time time = Time.from(originTime);
        //when & then
        assertAll(
                () -> assertEquals(time.getHour(), "09"),
                () -> assertEquals(time.getMinute(), "59")
        );
    }

    @Test
    void 등교_기본값_객체_반환() {
        //given
        Time time = Time.makeAbsentValue();

        //when & then
        assertAll(
                () -> assertEquals(time.getHour(), "--"),
                () -> assertEquals(time.getMinute(), "--")
        );
    }

    @Test
    void 시가_숫자가_아닐_경우() {
        //given
        String timeName = "ab:30";

        //when & then
        Assertions.assertThatThrownBy(() -> Time.from(timeName))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NUMBER_FORMAT_HOUR_ERROR.getMessage());
    }

    @Test
    void 분이_숫자가_아닐_경우() {
        //given
        String invalidTime = "03:ab";

        //when & then
        Assertions.assertThatThrownBy(() -> Time.from(invalidTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NUMBER_FORMAT_MINUTE_ERROR.getMessage());
    }

    @Test
    void 콜론_검사() {
        //given
        String timeName = "10;31";

        //when & then
        Assertions.assertThatThrownBy(() -> Time.from(timeName))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.COLON_ERROR.getMessage());
    }
}
