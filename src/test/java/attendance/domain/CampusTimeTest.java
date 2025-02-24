package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.exception.CustomException;
import attendance.exception.ErrorMessage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CampusTimeTest {

    @Test
    void 등교_시간_객체_반환() {
        // given
        String time = "09:59";
        CampusTime campusTime = CampusTime.fromHourColonMinute(time);
        // when // then
        assertThat(campusTime.getHour()).isEqualTo(9);
        assertThat(campusTime.getMinute()).isEqualTo(59);
    }

    @Test
    void 구분자가_콜론이_아닐_경우() {
        //given
        String time = "10;31";
        //when // then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.SEPARATE_WITH_COLON_ERROR.getMessage());
    }

    @Test
    void 구분자가_콜론이_두개_이상_들어갈_경우() {
        // given
        String time = "09::59";
        // when // then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.SEPARATE_WITH_COLON_ERROR.getMessage());
    }

    @Test
    void 시가_숫자가_아닐_경우() {
        //given
        String time = "ab:30";
        //when // then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }

    @Test
    void 분이_숫자가_아닐_경우() {
        //given
        String time = "03:ab";
        //when // then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.TIME_FORMAT_ERROR.getMessage());
    }

    @Test
    void 시가_유효한_범위를_초과할_경우() {
        //given
        String time = "25:00";
        //when //then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.OUT_OF_HOUR_RANGE.getMessage());
    }

    @Test
    void 분이_유효한_범위를_초과할_경우() {
        //given
        String time = "24:61";
        //when //then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.OUT_OF_MINUTE_RANGE.getMessage());
    }

    @ParameterizedTest
    @CsvSource(value = {
            "07:59",
            "23:01"
    })
    void 캠퍼스_운영_시간이_아닌경우(String time) {
        // when // then
        Assertions.assertThatThrownBy(() -> CampusTime.fromHourColonMinute(time))
                .isInstanceOf(CustomException.class)
                .hasMessage(ErrorMessage.OUT_OF_CAMPUS_TIME_RANGE.getMessage());
    }

}
