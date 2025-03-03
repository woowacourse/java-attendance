package test.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import model.attendance.Attendance;
import model.attendance.AttendanceStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @DisplayName("출석 객체가 시작시간 5분 이내 출석이면 출석 상태를 반환한다.")
    @Test
    void success_returnNormalStatus() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 5));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.NORMAL);
    }

    @DisplayName("출석 객체가 시작시간 30분 이내 출석이면 지각 상태를 반환한다.")
    @Test
    void success_returnLateStatus() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 6));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.LATE);
    }

    @DisplayName("출석 객체가 시작시간 5분 이내 출석이면 출석 상태를 반환한다.")
    @Test
    void success_returnAbsenceStatus() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 31));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.ABSENCE);
    }

    @DisplayName("출결 상태 반환시, 월요일은 13시 시작으로 처리한다.")
    @Test
    void success_returnNormalStatusWhenMonday() {
        //given
        Attendance attendance = new Attendance(LocalDate.of(2024, 12, 9), LocalTime.of(13, 5));

        //then, when
        assertThat(attendance.findStatus()).isEqualTo(AttendanceStatus.NORMAL);
    }

}
