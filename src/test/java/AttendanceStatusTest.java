import domain.AttendTime;
import domain.AttendanceHistory;
import domain.AttendanceStatus;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceStatusTest {

    @Test
    void test1() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        AttendanceStatus attendanceStatus = new AttendanceStatus(attendanceHistory.calculateOnTime(), attendanceHistory.calculateLate(), attendanceHistory.calculateAbsent());
        assertThat(attendanceStatus.getStatus()).isEqualTo("면담");
    }

    @Test
    void test2() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        AttendanceStatus attendanceStatus = new AttendanceStatus(attendanceHistory.calculateOnTime(), attendanceHistory.calculateLate(), attendanceHistory.calculateAbsent());
        assertThat(attendanceStatus.getStatus()).isEqualTo(null);
    }

    @Test
    void test3() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));

        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        AttendanceStatus attendanceStatus = new AttendanceStatus(attendanceHistory.calculateOnTime(), attendanceHistory.calculateLate(), attendanceHistory.calculateAbsent());
        assertThat(attendanceStatus.getStatus()).isEqualTo("제적");
    }
}
