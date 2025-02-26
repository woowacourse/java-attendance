package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Attendance;
import model.AttendanceHistory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    @DisplayName("한 크루의 전체 출석 기록을 반환한다.")
    @Test
    void test() {
        //given
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0));
        attendanceHistory.register(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6));
        attendanceHistory.register(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31));

        //when
        List<Attendance> attendanceHistories = attendanceHistory.findAll();

        //then
        assertThat(attendanceHistories.size()).isEqualTo(31);
        assertThat(attendanceHistories).contains(new Attendance(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0)));
        assertThat(attendanceHistories).contains(new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6)));
        assertThat(attendanceHistories).contains(new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31)));
    }
}

