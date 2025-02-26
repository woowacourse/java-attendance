import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceStatusTest {
    @DisplayName("출석 시간이 교육 시작 시간으로부터 5분 이내일 경우 출석을 반환한다.")
    @Test
    void test() {
        AttendanceStatus attendanceStatus = new AttendanceStatus();
        DayOfWeek dayOfWeek = DayOfWeek.MONDAY;
        LocalTime time = LocalTime.of(13, 05);
        assertThat(attendanceStatus.getStatus(dayOfWeek, time)).isEqualTo("출석");
    }
}
