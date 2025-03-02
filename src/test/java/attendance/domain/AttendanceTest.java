package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {
    private Attendance attendance;

    @BeforeEach
    void setUp() {
        CrewName crewName = new CrewName("초코");
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse("2025-02-27"));
        AttendanceTime attendanceTime = new AttendanceTime("09:00");

        attendance = new Attendance(crewName, attendanceDate, attendanceTime);
    }

    @DisplayName("정상: 동일한 크루 이름 보유 여부 확인")
    @Test
    void successExecutionSameCrewName() {
        CrewName crewName = new CrewName("초코");
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse("2025-02-26"));
        AttendanceTime attendanceTime = new AttendanceTime("10:00");

        Attendance attendance2 = new Attendance(crewName, attendanceDate, attendanceTime);

        assertThat(attendance.hasSameCrewName(attendance2.getCrewName())).isTrue();
    }

    @DisplayName("정상: 동일한 출석 날짜 보유 여부 확인")
    @Test
    void successExecutionSameDate() {
        CrewName crewName = new CrewName("쿠키");
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.parse("2025-02-27"));
        AttendanceTime attendanceTime = new AttendanceTime("10:00");

        Attendance attendance2 = new Attendance(crewName, attendanceDate, attendanceTime);

        assertThat(attendance.hasSameAttendanceDate(attendance2.getAttendanceDate())).isTrue();
    }

    @DisplayName("정상: 해당 크루의 출석 날짜에 대한 출석 시간 및 출결 유형 수정 확인")
    @Test
    void successExecutionModifyAttendance() {
        AttendanceTime attendanceTime = new AttendanceTime("10:07");

        attendance.modifyAttendance(attendanceTime);

        assertThat(tuple(attendance.getAttendanceTime(), attendance.getAttendanceType()))
                .isEqualTo(tuple(attendanceTime.getAttendanceTime(), "지각"));
    }
}
