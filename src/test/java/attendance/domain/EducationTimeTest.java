package attendance.domain;

import static attendance.error.ErrorMessage.NO_EDUCATION_DAY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

class EducationTimeTest {

    @DisplayName("월요일은 교육시작시간이 13시이다")
    @Test
    void test_getStartTime_Monday() {
        // given
        EducationTime monday = EducationTime.MONDAY;

        // when
        LocalTime startTime = monday.getStartTime();

        // then
        assertThat(startTime).isEqualTo(LocalTime.of(13, 0));
    }

    @DisplayName("월요일이 아닌 요일의 교육 시작 시간은 10시이다")
    @ParameterizedTest
    @EnumSource(value = EducationTime.class, mode = EnumSource.Mode.EXCLUDE, names = {"MONDAY"})
    void test_getStartTime_NotMonday(EducationTime educationTime) {
        // when
        LocalTime startTime = educationTime.getStartTime();

        // then
        assertThat(startTime).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("교육이 없는 요일을 생성하려고 하면 예외가 발생한다")
    @Test
    void test_notEducationTime_error() {
        DayOfWeek saturday = DayOfWeek.SATURDAY;

        assertThatThrownBy(() -> EducationTime.from(saturday))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(NO_EDUCATION_DAY);
    }
}
