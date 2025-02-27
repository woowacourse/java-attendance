package attendance.service;

import attendance.domain.Attendance;
import attendance.domain.AttendanceFileParser;
import attendance.domain.AttendanceStatus;
import attendance.domain.Attendances;
import attendance.dto.AttendanceRemarkDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.*;

public class AttendanceServiceTest {

    @Test
    void 출석_데이터를_읽어온다() {
        // given
        Attendances expectedAttendances = new Attendances();
        LocalDate attendanceDate = LocalDate.of(2024, 12, 13);
        expectedAttendances.addAttendance("쿠키", new Attendance(attendanceDate, LocalTime.of(10, 8)));
        expectedAttendances.addAttendance("빙봉", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendances.addAttendance("빙티", new Attendance(attendanceDate, LocalTime.of(10, 7)));
        expectedAttendances.addAttendance("이든", new Attendance(attendanceDate, LocalTime.of(10, 7)));

        // when
        AttendanceService service = new AttendanceService(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );

        // then
        assertThat(service).extracting("attendances").isEqualTo(expectedAttendances);
    }

    @Test
    void 출석_성공시_dto객체를_반환한다() {
        // given
        AttendanceService service = new AttendanceService(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024,12, 16);
        LocalTime attendanceTime = LocalTime.of(12, 50);

        // when
        AttendanceRemarkDto dto = service.remarkAttendance("빙봉", attendanceDate, attendanceTime);

        // then
        assertThat(dto).isEqualTo(AttendanceRemarkDto.of(attendanceDate, attendanceTime, AttendanceStatus.PRESENCE));
    }

    @Test
    void 출석_날짜가_캠퍼스_운영일이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceService service = new AttendanceService(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024,12, 14);
        LocalTime attendanceTime = LocalTime.of(12, 50);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉",attendanceDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 12월 14일 토요일은 등교일이 아닙니다.");
    }

    @Test
    void 출석_시간이_캠퍼스_운영시간이_아닌_경우_예외를_반환한다() {
        // given
        AttendanceService service = new AttendanceService(
            new AttendanceFileParser("src/test/java/resources/testAttendances.csv")
        );
        LocalDate attendanceDate = LocalDate.of(2024,12, 16);
        LocalTime attendanceTime = LocalTime.of(23, 59);

        // when & then
        assertThatThrownBy(() -> service.remarkAttendance("빙봉", attendanceDate, attendanceTime))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("[ERROR] 캠퍼스 운영시간이 아닙니다.");
    }
}
