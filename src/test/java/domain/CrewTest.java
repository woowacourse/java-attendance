package domain;

import domain.attendance.AttendanceDate;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {
    @DisplayName("초기화 시 크루의 출석부 정보가 반영된다.")
    @Test
    void test() {
        // given
        Crew sooyang = new Crew("수양", LocalDate.of(2025, 2, 25));

        // when
        sooyang.initializeAttendance(List.of(
                LocalDateTime.of(2025, 2, 3, 13, 0)
        ));

        // then
        Assertions.assertThat(sooyang.findAttendanceDate(LocalDate.of(2025, 2, 3)).getAttendanceTime())
                .isEqualTo(LocalDateTime.of(2025, 2, 3, 13, 0));

    }

    @DisplayName("날짜를 입력하면 해당 날짜의 출석부를 확인할 수 있다")
    @Test
    void test2() {
        // given
        Crew sooyang = new Crew("수양", LocalDate.of(2025, 2, 25));
        sooyang.initializeAttendance(List.of(
                LocalDateTime.of(2025, 2, 3, 13, 0)
        ));

        // when
        AttendanceDate attendanceDate = sooyang.findAttendanceDate(LocalDate.of(2025, 2, 3));

        // then
        Assertions.assertThat(attendanceDate.getAttendanceTime()).isEqualTo(LocalDateTime.of(2025, 2, 3, 13, 0));
    }
}
