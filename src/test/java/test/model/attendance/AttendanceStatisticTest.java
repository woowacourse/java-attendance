package test.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;

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

public class AttendanceStatisticTest {

    @DisplayName("한 크루의 일부 출석 기록으로 출석, 지각, 결석 횟수를 구한다.")
    @Test
    void success_calculateAttendanceCount() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)), //출석
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 6)), //지각
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 31)), //결석
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(0, 0)), //결석
                new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(0, 0)) //결석
        );

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
    void success_findNonePenaltyStatus() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)), //출석
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)), //출석
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)) //결석
        );

        //when
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.NONE);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 경고")
    @Test
    void success_findWarningPenaltyStatus() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)), //출석
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)) //결석
        );

        //when
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.WARNING);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 면담")
    @Test
    void success_findMeetingPenaltyStatus() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(13, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)), //지각
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 31)), //결석
                new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)) //결석
        );

        //when
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.MEETING);
    }

    @DisplayName("한 크루의 일부 출석 기록으로 패널티 여부를 구한다. - 제적")
    @Test
    void success_findExpelledPenaltyStatus() {
        //given
        List<Attendance> attendances = List.of(
                new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(0, 0)), //결석
                new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(0, 0)), //결석
                new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(0, 0)), //결석
                new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(0, 0)), //결석
                new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(0, 0)), //결석
                new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(0, 0)) //결석
        );

        //when
        AttendanceStatistic attendanceStatistic = AttendanceStatistic.from(attendances);
        PenaltyStatus penaltyStatus = attendanceStatistic.getPenaltyStatus();

        //then
        assertThat(penaltyStatus).isEqualTo(PenaltyStatus.EXPELLED);
    }
}
