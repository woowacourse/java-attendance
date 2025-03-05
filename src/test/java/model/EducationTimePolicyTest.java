package model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.DayOfWeek;
import java.time.LocalTime;
import model.policy.EducationTimePolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class EducationTimePolicyTest {

    @DisplayName("요일에 맞는 교육 시작 시간을 가져온다")
    @ParameterizedTest
    @CsvSource({
            "MONDAY, 13:00",
            "TUESDAY, 10:00",
            "WEDNESDAY, 10:00",
            "THURSDAY, 10:00",
            "FRIDAY, 10:00"
    })
    void getEducationStartTimeTest(final DayOfWeek dayOfWeek, final LocalTime time) {
        assertThat(EducationTimePolicy.getStartTime(dayOfWeek)).isEqualTo(time);
    }

    @DisplayName("캠퍼스 운영 요일이 아닐 경우 예외가 발생한다")
    @ParameterizedTest
    @CsvSource({"SATURDAY", "SUNDAY"})
    void weekendGetEducationStartTime(final DayOfWeek dayOfWeek) {
        assertThatThrownBy(() -> EducationTimePolicy.getStartTime(dayOfWeek))
                .isInstanceOf(IllegalStateException.class);
    }
}
