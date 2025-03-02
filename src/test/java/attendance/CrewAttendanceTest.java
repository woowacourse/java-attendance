package attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewAttendanceTest {
    @DisplayName("신규 출석 기록 추가 성공")
    @Test
    void test1() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.parse("10:00");
        LocalDateTime attendance = LocalDateTime.of(date, time);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);

        assertThat(crewAttendance.hasRecord(date)).isTrue();
    }

    @DisplayName("이미 존재하는 날짜의 출석 기록 추가 시 예외 발생")
    @Test
    void test2() {
        LocalDate date = LocalDate.of(2024, 12, 3);
        LocalTime time = LocalTime.parse("10:00");
        LocalDateTime attendance = LocalDateTime.of(date, time);
        CrewAttendance crewAttendance = new CrewAttendance();

        crewAttendance.add(attendance);

        assertThatThrownBy(() -> crewAttendance.add(attendance))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재합니다. 출석 수정 기능을 이용해주세요.");
    }
}
