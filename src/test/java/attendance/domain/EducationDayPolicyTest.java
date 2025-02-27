package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EducationDayPolicyTest {

    @DisplayName("주말은 등교일이 아니다")
    @Test
    void test_weekend_isNotEducationDay() {
        // given
        EducationDayPolicy educationDayPolicy = new EducationDayPolicy(Set.of());
        LocalDate wantAttendanceDate = LocalDate.of(2024, 12, 1);

        // when
        boolean canAttendance = educationDayPolicy.isEducationDay(wantAttendanceDate);

        // then
        assertThat(canAttendance).isFalse();
    }

    @DisplayName("평일은 등교일이다.")
    @Test
    void test_weekday_isEducationDay() {
        // given
        EducationDayPolicy educationDayPolicy = new EducationDayPolicy(Set.of());
        LocalDate wantAttendanceDate = LocalDate.of(2024, 12, 2);

        // when
        boolean canAttendance = educationDayPolicy.isEducationDay(wantAttendanceDate);

        // then
        assertThat(canAttendance).isTrue();
    }

    @DisplayName("공휴일은 등교일이 아니다.")
    @Test
    void test_holiday_isNotEducationDay() {
        // given
        Set<LocalDate> holidays = Set.of(LocalDate.of(2024, 12, 25));
        EducationDayPolicy educationDayPolicy = new EducationDayPolicy(holidays);

        LocalDate wantAttendanceDate = LocalDate.of(2024, 12, 25);

        // when
        boolean canAttendance = educationDayPolicy.isEducationDay(wantAttendanceDate);

        // then
        assertThat(canAttendance).isFalse();
    }
}