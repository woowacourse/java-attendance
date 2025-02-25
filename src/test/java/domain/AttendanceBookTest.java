package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import util.DateTimeConvertor;

public class AttendanceBookTest {

    @DisplayName("출석부 생성 테스트")
    @Test
    void generateAttendanceBookTest() {
        Map<String, List<String>> crewAttendances = new HashMap<>();
        assertDoesNotThrow(() -> new AttendanceBook(crewAttendances));
    }

    @DisplayName("닉네임으로 크루 탐색 기능 테스트")
    @Test
    void findCrewByNameTest() {
        Map<String, List<String>> crewAttendances = new HashMap<>();
        crewAttendances.put("쿠키", List.of("2024-12-02 10:01", "2024-12-13 10:08"));
        crewAttendances.put("빙봉", List.of("2024-12-02 10:01", "2024-12-13 10:08"));
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        assertThat(attendanceBook.findCrewByName("쿠키"))
                .isEqualTo(new Crew("쿠키", new Attendances(List.of())));
    }

    @DisplayName("없는 크루 탐색 테스트")
    @Test
    void findCrewByNameExceptionTest() {
        Map<String, List<String>> crewAttendances = new HashMap<>();
        crewAttendances.put("쿠키", List.of("2024-12-02 10:01", "2024-12-13 10:08"));
        crewAttendances.put("빙봉", List.of("2024-12-02 10:01", "2024-12-13 10:08"));
        AttendanceBook attendanceBook = new AttendanceBook(crewAttendances);

        assertThatThrownBy(() -> attendanceBook.findCrewByName("메이"))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
