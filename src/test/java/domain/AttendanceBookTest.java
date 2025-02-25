package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    @DisplayName("출석부 생성 테스트")
    @Test
    void generateAttendanceBookTest() {
        Map<String, List<LocalDateTime>> crewAttendances = new HashMap<>();
        assertDoesNotThrow(() -> new AttendanceBook(crewAttendances));
    }

    @DisplayName("닉네임으로 크루 탐색 기능 테스트")
    @Test
    void findCrewByNameTest() {
        Map<String, List<LocalDateTime>> crewAttendances = new HashMap<>();
        crewAttendances.put("쿠키", List.of(LocalDateTime.of(2024, 12, 2, 10, 1)));
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        assertThat(attendanceBook.findCrewByName("쿠키"))
                .isEqualTo(new Crew("쿠키", new Attendances(List.of())));
    }

    @DisplayName("없는 크루 탐색 테스트")
    @Test
    void findCrewByNameExceptionTest() {
        Map<String, List<LocalDateTime>> crewAttendances = new HashMap<>();
        crewAttendances.put("쿠키", List.of(
                LocalDateTime.of(2024, 12, 2, 10, 1),
                LocalDateTime.of(2024, 12, 13, 10, 8)));
        crewAttendances.put("빙봉", List.of(
                LocalDateTime.of(2024, 12, 2, 10, 1),
                LocalDateTime.of(2024, 12, 13, 10, 8)));
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        assertThatThrownBy(() -> attendanceBook.findCrewByName("메이"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
