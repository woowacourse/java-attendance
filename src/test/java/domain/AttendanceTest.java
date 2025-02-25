package domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @DisplayName("출석 날짜와 시간 기반 출석 기록 생성 테스트")
    @Test
    void generateAttendanceTest() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 2));
        AttendanceTime attendanceTime = new AttendanceTime(LocalTime.of(10, 0));

        assertDoesNotThrow(() -> new Attendance(attendanceDate, attendanceTime));
    }

    @DisplayName("출석 시간 수정 테스트")
    @Test
    void editAttendanceTimeTest() {
        AttendanceDate attendanceDate = new AttendanceDate(LocalDate.of(2024, 12, 3));
        AttendanceTime oldTime = new AttendanceTime(LocalTime.of(10, 35));
        AttendanceTime newTime = new AttendanceTime(LocalTime.of(9, 55));

        Attendance attendance = new Attendance(attendanceDate, oldTime);
        attendance.editTime(newTime);

        Assertions.assertThat(attendance.isLate()).isEqualTo(false);
    }
}
