import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendTime;
import domain.AttendanceHistory;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @DisplayName("이미 출석된 경우 예외를 발생시킨다.")
    @Test
    void test4() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThatThrownBy(() -> attendanceHistory.addAttendance(new AttendTime("2024-12-13 10:31")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("[ERROR] 이미 출석 기록이 존재하는 날짜입니다. 수정 기능을 이용해주세요.");
    }

    @DisplayName("출석 횟수를 반환한다.")
    @Test
    void test1() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 09:59"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThat(attendanceHistory.calculateAttended()).isEqualTo(1);
    }

    @DisplayName("지각 횟수를 반환한다.")
    @Test
    void test2() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 10:06"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThat(attendanceHistory.calculateLate()).isEqualTo(1);
    }

    @DisplayName("결석 횟수를 반환한다.")
    @Test
    void test3() {
        List<AttendTime> attendTimes = new ArrayList<>();
        attendTimes.add(new AttendTime("2024-12-13 10:31"));
        AttendanceHistory attendanceHistory = new AttendanceHistory(attendTimes);

        assertThat(attendanceHistory.calculateAbsent()).isEqualTo(21);
    }
}
