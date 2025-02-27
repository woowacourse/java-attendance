package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceFileParser;
import attendance.domain.Attendances;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceServiceTest {

    @Test
    void 출석_데이터를_읽어온다() {
        // given
        AttendanceService service = new AttendanceService(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );

        Attendances expectedAttendances = new Attendances();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);
        expectedAttendances.addAttendance("쿠키", new Attendance(attendanceDate, LocalTime.of(10, 8)));
        expectedAttendances.addAttendance("빙봉", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendances.addAttendance("빙티", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendances.addAttendance("이든", new Attendance(attendanceDate, LocalTime.of(10, 7)));

        // when
        service.readAttendance();

        // then
        assertThat(service).extracting("attendances").isEqualTo(expectedAttendances);
    }
}
