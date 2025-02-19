import domain.AttendTime;
import domain.AttendanceHistory;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AttendanceHistoryTest {

    @Test
    void test1(){
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThat(attendanceHistory.calculateOnTime()).isEqualTo(1);
    }
    @Test
    void test2(){
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 10:06"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThat(attendanceHistory.calculateLate()).isEqualTo(1);
    }
    @Test
    void test3(){
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 10:31"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThat(attendanceHistory.calculateAbsent()).isEqualTo(21);
    }
}
