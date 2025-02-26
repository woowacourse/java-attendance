package domain.attendance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.crew.Crew;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceBookTest {

    private AttendanceBook attendanceBook;
    private Crew crew;
    private AttendanceLogs attendanceLogs;

    @BeforeEach
    void setUp() {
        crew = new Crew("행성");
        attendanceLogs = new AttendanceLogs();
        attendanceLogs.registerLog(LocalDateTime.of(2025, 2, 24, 10, 0));
        attendanceLogs.registerLog(LocalDateTime.of(2025, 2, 25, 10, 0));
        attendanceBook = new AttendanceBook(Map.of(crew, attendanceLogs));
    }

    @Test
    @DisplayName("크루의 출결 기록 검색 기능 테스트")
    void 크루의_출결_기록_검색_기능_테스트() {
        // when
        String crewName = crew.getName();
        // then & given
        assertEquals(attendanceBook.findCrewAttendanceLogs(crewName), attendanceLogs);
    }

    @Test
    @DisplayName("크루의 출결 기록 검색 예외 테스트")
    void 크루의_출결_기록_검색_예외_테스트() {
        // when
        String crewName = "토성";
        // then & given
        assertThatThrownBy(() -> attendanceBook.findCrewAttendanceLogs(crewName))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 확인 기능 테스트")
    void 출석_확인_기능_테스트() {
        // given
        String crewName = crew.getName();
        LocalDateTime attendDateTime = LocalDateTime.of(2025, 2, 26, 10, 0);
        // when & then
        assertTrue(attendanceBook.registerAttendanceLog(crewName, attendDateTime)
                .isAttendDate(attendDateTime.toLocalDate()));
    }

    @Test
    @DisplayName("출석 확인 예외 테스트")
    void 출석_확인_예외_테스트() {
        // given
        String crewName = crew.getName();
        LocalDateTime attendDateTime = LocalDateTime.of(2025, 2, 25, 11, 0);
        // when & then
        assertThatThrownBy(() -> attendanceBook.registerAttendanceLog(crewName, attendDateTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 수정 기능 테스트")
    void 출석_수정_기능_테스트() {
        // given
        String crewName = crew.getName();
        LocalDate editDate = LocalDate.of(2025, 2, 25);
        LocalTime editTime = LocalTime.of(11, 0);
        // when & then
        assertEquals(attendanceBook.editAttendanceLog(crewName, editDate, editTime).getAttendanceTime(), editTime);
    }

    @Test
    @DisplayName("출석 수정 예외 테스트")
    void 출석_수정_예외_테스트() {
        // given
        String crewName = crew.getName();
        LocalDate editDate = LocalDate.of(2025, 2, 26);
        LocalTime editTime = LocalTime.of(11, 0);
        // when & then
        assertThatThrownBy(() -> attendanceBook.editAttendanceLog(crewName, editDate, editTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("크루별 출석 기록 확인 기능 테스트")
    void 크루별_출석_기록_확인_기능_테스트() {
        // given
        String crewName = crew.getName();
        // when
        AttendanceLogs crewAttendanceLogs = attendanceBook.findCrewAttendanceLogs(crewName);
        // then
        assertEquals(attendanceLogs, crewAttendanceLogs);
    }
}
