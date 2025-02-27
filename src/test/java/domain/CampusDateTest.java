package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class CampusDateTest {

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

}
