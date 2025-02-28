package test;

import static org.assertj.core.api.Assertions.assertThat;

import common.Campus;
import common.Common;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import model.attendance.Attendance;
import model.attendance.AttendanceHistory;
import model.attendance.AttendanceStatistic;
import model.attendance.AttendanceStatus;
import model.attendance.PenaltyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    /**
     * Disabled
     * 크루 기록 전체 반환은 필요 없게됨
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
                new Attendance(LocalDate.of(2024, 12, 9), Campus.noneAttendanceTime));
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

        List<Attendance> attendances = attendanceHistory.sliceByDateUntilBefore(requestDate);

        //when
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);
        Map<AttendanceStatus, Integer> attendanceCount = attendanceStatistic.getAttendanceCount();

        //then
        assertThat(attendanceCount.get(AttendanceStatus.NORMAL)).isEqualTo(1);
        assertThat(attendanceCount.get(AttendanceStatus.LATE)).isEqualTo(1);
        assertThat(attendanceCount.get(AttendanceStatus.ABSENCE)).isEqualTo(3);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 패널티 없음")
    @Test
    void test3() {
        //given
        LocalDate requestDate = LocalDate.of(2024, 12, 7);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)); //출석
        attendanceHistory.register(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)); //출석
        attendanceHistory.register(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)); //결석

        List<Attendance> attendances = attendanceHistory.sliceByDateUntilBefore(requestDate);
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);

        //when
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.NONE);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 경고")
    @Test
    void test3_1() {
        //given
        LocalDate requestDate = LocalDate.of(2024, 12, 7);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)); //출석
        attendanceHistory.register(LocalDate.of(2024, 12, 3), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)); //결석

        List<Attendance> attendances = attendanceHistory.sliceByDateUntilBefore(requestDate);
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);

        //when
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.WARNING);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 면담")
    @Test
    void test3_2() {
        //given
        LocalDate requestDate = LocalDate.of(2024, 12, 7);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 2), LocalTime.of(13, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 3), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)); //지각
        attendanceHistory.register(LocalDate.of(2024, 12, 5), LocalTime.of(10, 31)); //결석

        List<Attendance> attendances = attendanceHistory.sliceByDateUntilBefore(requestDate);
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);

        //when
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.MEETING);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 제적")
    @Test
    void test3_3() {
        //given
        LocalDate requestDate = LocalDate.of(2024, 12, 13);
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        List<Attendance> attendances = attendanceHistory.sliceByDateUntilBefore(requestDate);
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);

        //when
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.EXPELLED);
    }
}

