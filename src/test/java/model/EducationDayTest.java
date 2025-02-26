package model;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.EducationDay;
import java.time.LocalDate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EducationDayTest {

    @Test
    @DisplayName("주말인 경우")
    void 입력한_날짜가_교육일인지_확인한다_1() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 8);

        // when
        boolean isDurationDay = EducationDay.isDuringEducationDay(date);

        // then
        assertThat(isDurationDay).isFalse();
    }

    @Test
    @DisplayName("운영중인 경우")
    void 입력한_날짜가_교육일인지_확인한다_2() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 10);

        // when
        boolean isDurationDay = EducationDay.isDuringEducationDay(date);

        // then
        assertThat(isDurationDay).isTrue();
    }

    @Test
    @DisplayName("공휴일인 경우")
    void 입력한_날짜가_교육일인지_확인한다_3() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 25);

        // when
        boolean isDurationDay = EducationDay.isDuringEducationDay(date);

        // then
        assertThat(isDurationDay).isFalse();
    }


}
