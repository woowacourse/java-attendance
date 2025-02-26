import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceCheckTest {

    private final AttendanceCheck attendanceCheck = new AttendanceCheck();
    private LocalTime lateStandardTime;
    private LocalTime absentStandardTime;

    @Test
    void 월요일_출석_테스트() {
        lateStandardTime = LocalTime.of(13, 5);
        absentStandardTime = LocalTime.of(13, 30);

        LocalTime attendanceTime = LocalTime.of(13, 0);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, lateStandardTime, absentStandardTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 월요일_출석_경계값_테스트() {
        lateStandardTime = LocalTime.of(13, 5);
        absentStandardTime = LocalTime.of(13, 30);

        LocalTime attendanceTime = LocalTime.of(13, 5);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, lateStandardTime, absentStandardTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @Test
    void 월요일_지각_테스트() {
        lateStandardTime = LocalTime.of(13, 5);
        absentStandardTime = LocalTime.of(13, 30);

        LocalTime attendanceTime = LocalTime.of(13, 30);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, lateStandardTime, absentStandardTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    void 월요일_지각_경계값_테스트() {
        lateStandardTime = LocalTime.of(13, 5);
        absentStandardTime = LocalTime.of(13, 30);

        LocalTime attendanceTime = LocalTime.of(13, 30);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, lateStandardTime, absentStandardTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.LATENESS);
    }

    @Test
    void 월요일_결석_테스트() {
        lateStandardTime = LocalTime.of(13, 5);
        absentStandardTime = LocalTime.of(13, 30);

        LocalTime attendanceTime = LocalTime.of(13, 31);
        AttendanceStatus attendanceStatus = attendanceCheck.judge(attendanceTime, lateStandardTime, absentStandardTime);
        assertThat(attendanceStatus).isEqualTo(AttendanceStatus.ABSENCE);
    }
}
