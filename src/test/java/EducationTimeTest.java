import exception.InvalidDateException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class EducationTimeTest {
    @DisplayName("월요일에 해당하는 교육 시간을 반환할 수 있다.")
    @Test
    void test1() {
        // given
        DayOfWeek monday = DayOfWeek.MONDAY;

        // when
        LocalTime startTime = EducationTime.startOf(monday);

        // then
        assertThat(startTime).isEqualTo(LocalTime.of(13, 0));
    }

    @DisplayName("화요일 ~ 금요일에 해당하는 교육 시간을 반환할 수 있다.")
    @Test
    void test2() {
        // given
        List<DayOfWeek> days = List.of(DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY);

        for (DayOfWeek day : days) {
            // when
            LocalTime startTime = EducationTime.startOf(day);

            // then
            assertThat(startTime).isEqualTo(LocalTime.of(10, 0));
        }
    }

    @DisplayName("주말 운영시간을 요청하는 경우, 예외가 발생한다.")
    @Test
    void test3() {
        // given
        List<DayOfWeek> days = List.of(DayOfWeek.SUNDAY, DayOfWeek.SATURDAY);

        for (DayOfWeek day : days) {
            // when & then
            assertThatThrownBy(() -> {
                EducationTime.startOf(day);
            }).isInstanceOf(InvalidDateException.class);
        }
    }

    @DisplayName("캠퍼스 운영 요일이 아닌 경우를 확인할 수 있다.")
    @Test
    void test4() {
        // given
        List<DayOfWeek> days = List.of(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

        for (DayOfWeek day : days) {
            // when
            final boolean isOperating = EducationTime.isOperatingOn(day);

            // then
            assertThat(isOperating).isFalse();
        }
    }

    @DisplayName("캠퍼스 운영 요일인 경우를 확인할 수 있다.")
    @Test
    void test5() {
        // given
        List<DayOfWeek> days = List.of(
                DayOfWeek.MONDAY, DayOfWeek.TUESDAY, DayOfWeek.WEDNESDAY, DayOfWeek.THURSDAY, DayOfWeek.FRIDAY
        );

        for (DayOfWeek day : days) {
            // when
            final boolean isOperating = EducationTime.isOperatingOn(day);

            // then
            assertThat(isOperating).isTrue();
        }
    }
}
