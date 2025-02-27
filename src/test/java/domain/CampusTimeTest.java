package domain;

import static org.assertj.core.api.Assertions.assertThat;

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

}
