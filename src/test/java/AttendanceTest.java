import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AttendanceTest {

    @Test
    void 닉네임과_등교시간_입력_시_출석한다() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("이든", "09:59")).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 닉네임과_등교시간_입력_시_5분_늦으면_지각이다() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("이든", "10:06")).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 닉네임과_등교시간_입력_시_30분_늦으면_결석이다() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("이든", "10:31")).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_출석() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("이든", "12:59")).isEqualTo(AttendanceStatus.ATTEND);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_지각() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("이든", "13:06")).isEqualTo(AttendanceStatus.LATE);
    }

    @Test
    void 월요일이면_교육시작_13시에_시작한다_결석() {
        Attendance attendance = new Attendance();
        assertThat(attendance.attend("이든", "13:31")).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
