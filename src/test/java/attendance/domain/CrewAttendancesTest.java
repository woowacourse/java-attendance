package attendance.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CrewAttendancesTest {

    private CrewAttendances crewAttendances;

    @BeforeEach
    void 초기화() {
        this.crewAttendances = new CrewAttendances();
        this.crewAttendances.initializeCrewAttendances(List.of(
                "쿠키,2025-02-19 10:35",
                "쿠키,2025-02-20 10:15",
                "쿠키,2025-02-21 10:08",
                "이든,2025-02-24 13:18"));
    }

    @Test
    void 파일로부터_초기화가_정상적으로_이루어진다() {
        // Given
        Crew crew = new Crew("쿠키");

        // When & Then
        assertThat(crewAttendances.isRegisteredCrew(crew))
                .isTrue();
    }

    @Test
    void 크루와_날짜를_주면_해당_출석_객체를_반환한다() {
        // Given
        Crew crew = new Crew("쿠키");
        LocalDate attendanceDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(10, 8);

        // When & Then
        assertThat(crewAttendances.findAttendanceByCrewAndLocalDate(crew, attendanceDate))
                .isEqualTo(new Attendance(new AttendanceDate(attendanceDate), new AttendanceTime(attendanceTime)));
    }

    @Test
    void 크루와_출석_객체를_알려주면_해당_출석_객체를_저장한다() {
        // Given
        Crew crew = new Crew("쿠키");
        LocalDate attendanceDate = LocalDate.of(2025, 2, 24);
        LocalTime attendanceTime = LocalTime.of(13, 00);

        // When
        crewAttendances.addAttendance(crew,
                new Attendance(new AttendanceDate(attendanceDate), new AttendanceTime(attendanceTime)));

        // Then
        assertThat(crewAttendances.findAttendanceByCrewAndLocalDate(crew, attendanceDate))
                .isEqualTo(new Attendance(new AttendanceDate(attendanceDate), new AttendanceTime(attendanceTime)));
    }

    @Test
    void 크루와_출석_객체를_알려주면_해당_출석_객체를_삭제한다() {
        // Given
        Crew crew = new Crew("쿠키");
        LocalDate attendanceDate = LocalDate.of(2025, 2, 21);
        LocalTime attendanceTime = LocalTime.of(10, 8);
        Attendance attendance = new Attendance(new AttendanceDate(attendanceDate), new AttendanceTime(attendanceTime));

        // When
        crewAttendances.removeAttendance(crew, attendance);

        // Then
        assertThatThrownBy(() -> crewAttendances.findAttendanceByCrewAndLocalDate(crew, attendanceDate))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("출석 기록이 존재하지 않습니다.");
    }
}
