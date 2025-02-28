package domain;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendancesTest {
    @DisplayName("오늘 날짜에 출석 정보가 존재하는지 확인한다")
    @Test
    void test() {
        // given
        LocalDateTime day = LocalDateTime.of(2025, 02, 27, 10, 0);
        Attendance day1 = new Attendance(day);
        Attendance day2 = new Attendance(day.minusDays(1));
        Attendances attendances = new Attendances(List.of(day1, day2));

        // when
        boolean hasDate = attendances.has(day.toLocalDate());

        // then
        Assertions.assertThat(hasDate).isTrue();
    }
}