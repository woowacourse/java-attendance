package domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    @DisplayName("출석 객체 생성 테스트")
    void test1() {
        //given
        final AttendanceDate attendanceDate1 = new AttendanceDate(LocalDate.of(2024, 12, 9));
        final AttendanceDate attendanceDate2 = new AttendanceDate(LocalDate.of(2024, 12, 13));
        final AttendanceTime attendanceTime1 = AttendanceTime.of(attendanceDate1.getDayOfWeek(), LocalTime.of(13, 5));
        final AttendanceTime attendanceTime2 = AttendanceTime.of(attendanceDate2.getDayOfWeek(), LocalTime.of(10, 6));
        final AttendanceStatus status1 = AttendanceStatus.ATTENDANCE;
        final AttendanceStatus status2 = AttendanceStatus.LATE;

        //should
        assertThatCode(() -> new Attendance(attendanceDate1, attendanceTime1, status1)).doesNotThrowAnyException();
        assertThatCode(() -> new Attendance(attendanceDate2, attendanceTime2, status2)).doesNotThrowAnyException();
    }

}
