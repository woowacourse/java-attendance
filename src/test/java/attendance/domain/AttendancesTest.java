package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancesTest {
    private Attendance attendance;
    private Attendances attendances;

    @BeforeEach
    void setUp() {
        attendances = new Attendances();
        CrewName crewName = new CrewName("초코");
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse("2025-02-27"));
        AttendanceTime attendanceTime = new AttendanceTime("09:07");
        attendance = new Attendance(crewName, attendanceDate, attendanceTime);
    }

    @DisplayName("정상: 출석 확인 기록 초기화의 정상 작동 확인")
    @Test
    void successExecutionInitializeAttendances() {
        attendances.initializeAttendances(Map.of("초코", List.of(LocalDateTime.parse("2025-02-28T09:07"))));
        assertThat(attendances.getAttendances()).isNotEmpty();
    }

    @DisplayName("정상: 출석 확인 기록 추가의 정상 작동 확인")
    @Test
    void successExecutionAddAttendance() {
        assertThatCode(() -> attendances.addAttendance(attendance)).doesNotThrowAnyException();
    }

    @DisplayName("예외: 크루가 동일한 날짜와 시간으로 출석 확인 시도 시 예외 발생")
    @Test
    void causeExceptionAddAttendance() {
        attendances.addAttendance(attendance);
        assertThatThrownBy(() -> attendances.addAttendance(attendance)).isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("정상: 크루 이름으로 출석 기록 조회 시 정상 작동 확인")
    @Test
    void successExecutionFindAttendance() {
        attendances.addAttendance(attendance);
        assertThatCode(() -> attendances.lookupCrewAttendance(new CrewName("초코"))).doesNotThrowAnyException();
    }

    @DisplayName("예외: 크루의 존재하지 않는 출석 날짜로 출석 기록 조회 시 예외 발생")
    @Test
    void causeExceptionFindAttendanceDate() {
        attendances.addAttendance(attendance);
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse("2025-02-28"));
        assertThatThrownBy(() -> attendances.findCrewAttendanceByDate(new CrewName("초코"), attendanceDate))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
