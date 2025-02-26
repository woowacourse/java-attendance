package test;

import static org.assertj.core.api.Assertions.assertThat;

import common.Common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import model.Attendance;
import model.AttendanceHistory;
import model.AttendanceStatistics;
import model.AttendanceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    /**
     * 전체 반환은 필요 없게됨
     */
//    @DisplayName("한 크루의 전체 출석 기록을 반환한다.")
//    @Test
//    void test() {
//        //given
//        AttendanceHistory attendanceHistory = new AttendanceHistory();
//        attendanceHistory.register(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0));
//        attendanceHistory.register(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6));
//        attendanceHistory.register(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31));
//
//        //when
//        List<Attendance> attendanceHistories = attendanceHistory.sliceByDateUntil();
//
//        //then
//        assertThat(attendanceHistories.size()).isEqualTo(31);
//        assertThat(attendanceHistories).contains(new Attendance(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0)));
//        assertThat(attendanceHistories).contains(new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6)));
//        assertThat(attendanceHistories).contains(new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31)));
//    }

    @DisplayName("한 크루의 일부 출석 기록을 반환한다.")
    @Test
    void test1() {
        //given
        int requestDate = 13;
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0));
        attendanceHistory.register(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6));
        attendanceHistory.register(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31));

        //when
        List<Attendance> attendanceHistories = attendanceHistory.sliceByDateUntilBefore(LocalDate.of(2024, 12, requestDate));

        //then
        assertThat(attendanceHistories.size()).isEqualTo(9);
        assertThat(attendanceHistories.get(5)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 9), Common.noneAttendanceTime));
        assertThat(attendanceHistories.get(6)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0)));
        assertThat(attendanceHistories.get(7)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6)));
        assertThat(attendanceHistories.get(8)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31)));
    }

    @DisplayName("한 크루의 일부 출석 기록으로 출석, 지각, 결석 횟수를 구한다.")
    @Test
    void test2() {
        //given
        LocalDate requestDate = LocalDate.of(2024, 12, 7);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)); //출석
        attendanceHistory.register(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31)); //결석


        //when
        AttendanceStatistics attendanceStatistics = new AttendanceStatistics(attendanceHistory);
        Map<AttendanceStatus, Integer> attendanceStatusCount = attendanceStatistics.calculateStatusCountUntilBefore(requestDate);


        //then
        assertThat(attendanceStatusCount.get(AttendanceStatus.NORMAL)).isEqualTo(1);
        assertThat(attendanceStatusCount.get(AttendanceStatus.LATE)).isEqualTo(1);
        assertThat(attendanceStatusCount.get(AttendanceStatus.ABSENCE)).isEqualTo(3);
    }
}

