package attendance.domain;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class CampusTimeTest {

    @Test
    void 등교_시간_객체_반환() {
        //given
        String originTime = "09:59";
        CampusTime campusTime = CampusTime.fromHourColonMinute(originTime);
        //when & then
        assertAll(
                () -> assertEquals(campusTime.getHour(), "09"),
                () -> assertEquals(campusTime.getMinute(), "59")
        );
    }

    @Test
    void 등교_기본값_객체_반환() {
        //given
        CampusTime campusTime = CampusTime.makeAbsentValue();

        //when & then
        assertAll(
                () -> assertEquals(campusTime.getHour(), "--"),
                () -> assertEquals(campusTime.getMinute(), "--")
        );
    }

    @Test
    void 시가_숫자가_아닐_경우() {
        //given
        String timeName = "ab:30";

        //when & then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(timeName))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }

    @Test
    void 분이_숫자가_아닐_경우() {
        //given
        String invalidTime = "03:ab";

        //when & then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(invalidTime))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.NUMBER_FORMAT_MINUTE_ERROR.getMessage());
    }

    @Test
    void 콜론_검사() {
        //given
        String timeName = "10;31";

        //when & then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(timeName))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.SEPARATE_WITH_COLON_ERROR.getMessage());
    }
}
