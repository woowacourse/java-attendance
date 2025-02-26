import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {
    @Test
    @DisplayName("같은 크루에 대한 출석 기록이라면 true 를 반환한다")
    void test1() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, attendAt);

        // when & then
        assertThat(attendanceHistory.isAboutSameCrew(crew)).isTrue();
    }

    @Test
    @DisplayName("다른 크루에 대한 출석 기록이라면 false 를 반환한다")
    void test2() {
        // given
        Crew crew = new Crew("히로");
        Crew differentCrew = new Crew("다로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, attendAt);

        // when & then
        assertThat(attendanceHistory.isAboutSameCrew(differentCrew)).isFalse();
    }

    @Test
    @DisplayName("같은 날짜에 대한 출석 기록이라면 true 를 반환한다")
    void test3() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, attendAt);

        LocalDate requestedDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThat(attendanceHistory.isAboutSameDate(requestedDate)).isTrue();
    }

    @Test
    @DisplayName("다른 날짜에 대한 출석 기록이라면 true 를 반환한다")
    void test4() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, attendAt);

        LocalDate requestedDate = LocalDate.of(2024, 12, 3);

        // when & then
        assertThat(attendanceHistory.isAboutSameDate(requestedDate)).isFalse();
    }
}

