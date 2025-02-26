import static org.assertj.core.api.Assertions.assertThat;

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
        attendanceHistories.update(crew, newHistory);

        // then
        assertThat(attendanceHistories.checkExistenceByCrewAndDate(crew, newAttendAt.toLocalDate())).isTrue();
    }
}
