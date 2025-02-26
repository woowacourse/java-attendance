package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import attendance.model.EducationSchedule;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class EducationScheduleTest {

    @Test
    void 특정_날짜의_운영시간을_조회한다() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 9);

        // when
        EducationSchedule educationSchedule = EducationSchedule.from(date);

        // then
        assertThat(educationSchedule).isEqualTo(EducationSchedule.MONDAY);
    }

    @Test
    void 운영_요일이_아닌_날짜로_운영시간을_조회하면_예외가_발생한다() {
        // given
        LocalDate date = LocalDate.of(2024, 12, 8);

        // when & then
        assertThatThrownBy(() -> EducationSchedule.from(date))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
