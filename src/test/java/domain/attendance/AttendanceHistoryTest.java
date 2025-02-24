package domain.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import domain.crew.Crew;
import domain.date.AttendanceDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AttendanceHistoryTest {
    @Test
    @DisplayName("출석 기록이 정상적으로 생성된다")
    void testOf() {
        // given
        Crew crew = Crew.of("히로");
        int day = 1;
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 10, 0);

        // when
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, attendanceDateTime);

        // then
        assertAll(
                () -> assertThat(attendanceHistory.getDay()).isEqualTo(day),
                () -> assertThat(attendanceHistory.hasSameDay(day)).isTrue()
        );
    }

    @Test
    @DisplayName("이름이 같은 크루에 대해서 같은 크루로 인식한다")
    void testAboutSameCrew() {
        // given
        Crew crew = Crew.of("히로");
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(1, 10, 0);
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, attendanceDateTime);

        // when & then
        assertThat(attendanceHistory.aboutSameCrew(Crew.of("히로"))).isTrue();
    }

    @Test
    @DisplayName("이전 날짜에 해당하는 출석 기록을 판단한다")
    void testIsPastHistory() {
        // given
        Crew crew = Crew.of("히로");
        int day = 3;
        AttendanceDateTime attendanceDateTime = AttendanceDateTime.of(day, 10, 0);
        AttendanceHistory attendanceHistory = AttendanceHistory.of(crew, attendanceDateTime);

        // when & then
        assertThat(attendanceHistory.isPastHistory(4)).isTrue();
    }
}
