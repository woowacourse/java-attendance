import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoriesTest {
    @Test
    @DisplayName("조건에 알맞은 출석 기록이 없다면 false 를 반환해라")
    void test1() {
        // given
        AttendanceHistories attendanceHistories = new AttendanceHistories(new ArrayList<>());
        Crew crew = new Crew("히로");
        LocalDate requestedDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThat(attendanceHistories.checkExistenceByCrewAndDate(crew, requestedDate)).isFalse();
    }

    @Test
    @DisplayName("조건에 알맞은 출석 기록이 있다면 true 를 반환해라")
    void test2() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistories attendanceHistories = new AttendanceHistories(
                List.of(new AttendanceHistory(crew, attendAt)));

        LocalDate requestedDate = LocalDate.of(2024, 12, 2);

        // when & then
        assertThat(attendanceHistories.checkExistenceByCrewAndDate(crew, requestedDate)).isTrue();
    }

    @Test
    @DisplayName("출석 기록을 수정해라")
    void test3() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistory oldHistory = new AttendanceHistory(crew, attendAt);

        LocalDateTime newAttendAt = LocalDateTime.of(2024, 12, 2, 10, 6);
        AttendanceHistory newHistory = new AttendanceHistory(crew, newAttendAt);
        AttendanceHistories attendanceHistories = new AttendanceHistories(new ArrayList<>(List.of(oldHistory)));

        // when
        attendanceHistories.update(oldHistory, newHistory);

        // then
        assertThat(attendanceHistories.findByCrewAndDate(crew, newAttendAt.toLocalDate())).isEqualTo(newHistory);
    }

    @Test
    @DisplayName("조건에 맞는 출석 기록을 찾는다")
    void test4() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);
        AttendanceHistory attendanceHistory = new AttendanceHistory(crew, attendAt);
        AttendanceHistories attendanceHistories = new AttendanceHistories(new ArrayList<>(List.of(attendanceHistory)));

        // when & then
        assertThat(attendanceHistories.findByCrewAndDate(crew, attendAt.toLocalDate())).isEqualTo(attendanceHistory);
    }

    @Test
    @DisplayName("조건에 맞는 출석 기록이 없는 경우 예외를 던진다")
    void test5() {
        // given
        AttendanceHistories attendanceHistories = new AttendanceHistories(List.of());
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);

        // when & then
        assertThatThrownBy(() -> attendanceHistories.findByCrewAndDate(crew, attendAt.toLocalDate()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("조건에 해당하는 기록이 존재하지 않습니다.");

    }

    @Test
    @DisplayName("특정 날짜 전까지의 특정 크루의 모든 출석 기록을 가져온다")
    void test6() {
        // given
        Crew crew = new Crew("히로");
        LocalDateTime attendAt = LocalDateTime.of(2024, 12, 2, 10, 0);

        AttendanceHistories attendanceHistories = new AttendanceHistories(List.of(
                new AttendanceHistory(crew, attendAt),
                new AttendanceHistory(crew, attendAt.plusDays(1)),
                new AttendanceHistory(crew, attendAt.plusDays(2)),
                new AttendanceHistory(crew, attendAt.plusDays(3)
                )));

        // when & then
        List<AttendanceHistory> result = attendanceHistories.findAllHistoriesOfCrewDateBefore(
                crew, LocalDate.of(2024, 12, 6));
        assertThat(result).hasSize(4);

    }
}
