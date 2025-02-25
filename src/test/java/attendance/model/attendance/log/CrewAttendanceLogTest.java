package attendance.model.attendance.log;

import static org.assertj.core.api.Assertions.assertThat;

import attendance.model.campus.CampusOperationPolicy;
import attendance.model.crew.Crew;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CrewAttendanceLogTest {

    private static final Crew crew = new Crew("crew");
    private static List<AttendanceLog> attendanceLogs;
    private static CrewAttendanceLog crewAttendanceLog;
    private final CampusOperationPolicy campusOperationPolicy = new CampusOperationPolicy();

    @BeforeAll
    static void setUp() {
        attendanceLogs = List.of();
        crewAttendanceLog = new CrewAttendanceLog(crew, new AttendanceLogs(attendanceLogs));
    }

    @DisplayName("LocalDateTime 을 받아 출석 기록 생성 후, 출석 기록 리스트에 추가한다")
    @Test
    void addAttendanceLog() {

        // Given
        final LocalDateTime attendanceDateTime = LocalDateTime.of(2024, 12, 13, 10, 1);
        final AttendanceLog expected = AttendanceLog.fromDateTime(
                attendanceDateTime,
                campusOperationPolicy
        );

        // When
        crewAttendanceLog.addAttendanceLog(expected);

        // Then
        assertThat(crewAttendanceLog.getAttendanceLogs()).containsOnly(expected);
    }
}
