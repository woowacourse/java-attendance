package domain.attendance;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import domain.crew.CrewStatus;
import exception.ErrorException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class AttendanceLogsTest {

    private AttendanceLogs attendanceLogs;

    @BeforeEach
    void setUp() {
        attendanceLogs = new AttendanceLogs();
        attendanceLogs.registerLog(LocalDateTime.of(2024, 12, 10, 10, 0));
        attendanceLogs.registerLog(LocalDateTime.of(2024, 12, 11, 10, 0));
    }

    @ParameterizedTest
    @DisplayName("크루의 전체 출결 기록 상태 테스트")
    @CsvSource({
            "'2', 'EXPEL'",
            "'2,3', 'CONSULT'",
            "'2,3,4,5,6', 'WARNING'",
            "'2,3,4,5,6,9', 'PASS'"

    })
    void 크루의_전체_출결_기록_상태_테스트(String days, String expectedStatus) {
        // given
        LocalDate todayDate = LocalDate.of(2024, 12, 11);
        AttendanceLogs attendanceLogs = new AttendanceLogs();
        for (String day : days.split(",")) {
            attendanceLogs.registerLog(LocalDateTime.of(2024, 12, Integer.parseInt(day), 10, 0));
        }
        // when & then
        assertEquals(CrewStatus.valueOf(expectedStatus), attendanceLogs.calculateCrewStatus(todayDate));
    }

    @Test
    @DisplayName("출석 확인 기능 테스트")
    void 출석_확인_기능_테스트() {
        // given
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 12, 10, 0);
        // when & then
        assertTrue(attendanceLogs.registerLog(attendDateTime).isAttendDate(attendDateTime.toLocalDate()));
    }

    @Test
    @DisplayName("출석 확인 예외 테스트")
    void 출석_확인_예외_테스트() {
        // given
        LocalDateTime attendDateTime = LocalDateTime.of(2024, 12, 11, 10, 0);
        // when & then
        assertThatThrownBy(() -> attendanceLogs.registerLog(attendDateTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }

    @Test
    @DisplayName("출석 수정 기능 테스트")
    void 출석_수정_기능_테스트() {
        // given
        LocalDate editDate = LocalDate.of(2024, 12, 10);
        LocalTime editTime = LocalTime.of(11, 0);
        // when & then
        assertEquals(attendanceLogs.editLog(editDate, editTime).getAttendanceTime(), editTime);
    }

    @Test
    @DisplayName("출석 수정 예외 테스트")
    void 출석_수정_예외_테스트() {
        // given
        LocalDate editDate = LocalDate.of(2024, 12, 12);
        LocalTime editTime = LocalTime.of(11, 0);
        // when & then
        assertThatThrownBy(() -> attendanceLogs.editLog(editDate, editTime))
                .isInstanceOf(ErrorException.class)
                .hasMessageContaining("[ERROR]");
    }
}

