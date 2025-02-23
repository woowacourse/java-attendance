package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewsTest {

    private Crews crews;

    @BeforeEach
    void beforeEach() {
        String username1 = "쿠키";
        String username2 = "빙봉";
        String username3 = "빙티";
        List<LocalDateTime> historiesTimes1 = List.of(LocalDateTime.of(2024, 12, 4, 9, 59),
                LocalDateTime.of(2024, 12, 3, 9, 59),
                LocalDateTime.of(2024, 12, 2, 9, 59));
        List<LocalDateTime> historiesTimes2 = List.of(LocalDateTime.of(2024, 12, 4, 10, 35),
                LocalDateTime.of(2024, 12, 3, 10, 35),
                LocalDateTime.of(2024, 12, 2, 9, 59));
        List<LocalDateTime> historiesTimes3 = List.of();
        Map<String, List<LocalDateTime>> crews = new HashMap<>();
        crews.put(username1, historiesTimes1);
        crews.put(username2, historiesTimes2);
        crews.put(username3, historiesTimes3);
        this.crews = new Crews(crews, LocalDate.of(2024, 12, 5));
    }

    @Test
    @DisplayName("특정 시간 전날들의 출석 기록 테스트")
    public void getCrewHistory() {
        // given
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        List<LocalDateTime> expected = List.of(LocalDateTime.of(2024, 12, 2, 9, 59),
                LocalDateTime.of(2024, 12, 3, 9, 59),
                LocalDateTime.of(2024, 12, 4, 9, 59));
        // when
        List<AttendanceHistory> result = crews.getBeforeHistory("쿠키", standard);
        List<LocalDateTime> resultTime = result.stream().map(AttendanceHistory::getAttendanceTime).toList();
        assertThat(resultTime).isEqualTo(expected);
    }

    @Test
    @DisplayName("조회 정렬 테스트")
    public void getSortedCrewHistory() {
        // given
        LocalDateTime standard = LocalDateTime.of(2024, 12, 19, 10, 35);
        List<LocalDateTime> expected = List.of(LocalDateTime.of(2024, 12, 2, 9, 59),
                LocalDateTime.of(2024, 12, 3, 10, 35),
                LocalDateTime.of(2024, 12, 4, 10, 35));
        // when
        List<AttendanceHistory> result = crews.getBeforeHistory("빙봉", standard);
        List<LocalDateTime> resultTime = result.stream().map(AttendanceHistory::getAttendanceTime).toList();
        // then
        assertThat(resultTime).isEqualTo(expected);
    }

    @Test
    @DisplayName("회원 출석 확인 테스트 - 특정 날짜에 출석이 되었는지 확인한다.")
    void attendanceTest() {
        // given
        String username = "빙봉";
        LocalDateTime attendanceTime = LocalDateTime.of(2024, 12, 19, 10, 35);
        crews.addHistory(username, attendanceTime);
        LocalDateTime standardTime = LocalDateTime.of(2024, 12, 20, 10, 35);
        // when
        List<AttendanceHistory> result = crews.getBeforeHistory(username, standardTime);
        List<LocalDateTime> resultTime = result.stream().map(AttendanceHistory::getAttendanceTime).toList();
        // then
        boolean check = resultTime.contains(attendanceTime);
        assertThat(check).isTrue();
    }

    @Test
    @DisplayName("제적 위험자 조회 기능 테스트")
    void getHighAbsenceLevelCrewsTest() {
        // when
        List<Crew> absenceLevelCrews = crews.getHighAbsenceLevelCrews(LocalDateTime.of(2024, 12, 5, 11, 0));
        // then
        assertThat(absenceLevelCrews.size()).isEqualTo(2);
        List<String> usernames = absenceLevelCrews.stream().map(Crew::getUserName).toList();
        assertThat(usernames.contains("빙봉")).isTrue();
        assertThat(usernames.contains("빙티")).isTrue();
    }

    @Test
    @DisplayName("크루 맴버가 없을 때 조회")
    void getCrew_Exception() {
        assertThatThrownBy(() -> crews.addHistory("a", LocalDateTime.of(2024, 12, 23, 0, 0)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 존재하지 않은 크루입니다.");
    }
}
