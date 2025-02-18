import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    void 등교시간_입력_시_출석한다() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,17,9,59,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 등교시간_입력_시_5분_늦으면_지각이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,17,10,6,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 등교시간_입력_시_30분_늦으면_결석이다() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,17,10,31,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_출석() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,16,12,59,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_지각() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,16,13,6,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_결석() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,16,13,31,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_결석2() {
        LocalDateTime dateTime = LocalDateTime.of(2024,12,16,14,1,0);
        Attendance attendance = new Attendance(dateTime);
        assertThat(attendance.checkAttendanceStatus(dateTime)).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
