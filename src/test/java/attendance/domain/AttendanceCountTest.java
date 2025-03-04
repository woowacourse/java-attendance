package attendance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCountTest {
    private AttendanceCount attendanceCount;
    private CrewName crewName;
    private AttendanceDate attendanceDate;

    @BeforeEach
    void setUp() {
        attendanceCount = new AttendanceCount();

        crewName = new CrewName("빙봉");
        attendanceDate = new AttendanceDate(LocalDate.of(2025, 2, 21));
    }

    @DisplayName("정상: 출석 횟수 계산 확인")
    @Test
    void successExecutionCheckSafeCount() {
        AttendanceTime attendanceTime = new AttendanceTime("10:00");

        Attendance attendance = new Attendance(crewName, attendanceDate, attendanceTime);
        attendanceCount.checkAttendanceCount(attendance.getAttendanceType());

        assertThat(attendanceCount.getSafeCount()).isEqualTo(1);
    }

    @DisplayName("정상: 지각 횟수 계산 확인")
    @Test
    void successExecutionCheckLateCount() {
        AttendanceTime attendanceTime = new AttendanceTime("10:07");

        Attendance attendance = new Attendance(crewName, attendanceDate, attendanceTime);
        attendanceCount.checkAttendanceCount(attendance.getAttendanceType());

        assertThat(attendanceCount.getLateCount()).isEqualTo(1);
    }

    @DisplayName("정상: 결석 횟수 계산 확인")
    @Test
    void successExecutionCheckAbsentCount() {
        AttendanceTime attendanceTime = new AttendanceTime("10:37");

        Attendance attendance = new Attendance(crewName, attendanceDate, attendanceTime);
        attendanceCount.checkAttendanceCount(attendance.getAttendanceType());

        assertThat(attendanceCount.getAbsentCount()).isEqualTo(1);
    }
}
