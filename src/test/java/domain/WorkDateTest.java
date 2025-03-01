package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.junit.jupiter.api.Test;

class WorkDateTest {

    @Test
    void 유효하지_않은_날짜를_생성하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> new WorkDate(2023, 12, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2024년 12월이 아닌 날짜는 등록할 수 없습니다.");

        assertThatThrownBy(() -> new WorkDate(2024, 11, 5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("2024년 12월이 아닌 날짜는 등록할 수 없습니다.");

        assertThatThrownBy(() -> new WorkDate(2024, 12, 32))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("일(day)은 1 이상 31 이하여야 합니다.");
    }

    @Test
    void 주말에는_출석할_수_없다() {
        // when & then
        assertThatThrownBy(() -> new WorkDate(2024, 12, 7))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 또는 공휴일에는 출석할 수 없습니다.");

        assertThatThrownBy(() -> new WorkDate(2024, 12, 8))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 또는 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 공휴일에는_출석할_수_없다() {
        // when & then
        assertThatThrownBy(() -> new WorkDate(2024, 12, 25))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주말 또는 공휴일에는 출석할 수 없습니다.");
    }

    @Test
    void 다음_출석_가능한_날짜를_반환한다() {
        // given
        WorkDate workDate = new WorkDate(2024, 12, 6);

        // when
        WorkDate nextWorkDate = workDate.plusDay();

        // then
        assertThat(nextWorkDate).isEqualTo(new WorkDate(2024, 12, 9));
    }

    @Test
    void 특정_LocalDate보다_이후인지_확인한다() {
        // given
        WorkDate workDate = new WorkDate(2024, 12, 10);
        LocalDate localDate = LocalDate.of(2024, 12, 9);

        // when
        boolean isAfter = workDate.isAfter(localDate);

        // then
        assertThat(isAfter).isTrue();
    }

    @Test
    void 요일에_맞는_WorkDay를_반환한다() {
        // given
        WorkDate workDate = new WorkDate(2024, 12, 6);

        // when
        WorkDay workDay = workDate.getWorkDay();

        // then
        assertThat(workDay).isEqualTo(WorkDay.FRIDAY);
    }
}
