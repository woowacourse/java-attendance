package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

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
    void 시간_및_분_범위를_초과하는_숫자_입력시_예외를_발생시킨다() {
        // given
        String givenData = "24:01";

        // when // then
        assertThatThrownBy(() -> CampusTime.from(givenData))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 유효한 범위의 숫자를 입력해 주세요.");
    }

}
