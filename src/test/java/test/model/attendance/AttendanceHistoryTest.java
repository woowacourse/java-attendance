package test.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import common.Campus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.attendance.Attendance;
import model.attendance.AttendanceHistory;
import model.exception.FutureAttendanceException;
import model.exception.HolidayAttendanceException;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

    /**
     * 출석 수정 관련
     */
    @DisplayName("수정하려는 날짜에 맞는 수정전 Attendance 객체를 찾는다.")
    @Test
    void success_findOldAttendanceByModifyDate() {
        //given
        LocalDate modifyDate = LocalDate.of(2024, 12, 13);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(modifyDate, LocalTime.of(10, 10));

        //when
        Attendance oldAttendance = attendanceHistory.findByDate(modifyDate);

        //then
        assertThat(oldAttendance).isEqualTo(new Attendance(modifyDate, LocalTime.of(10, 10)));
    }

    @DisplayName("등교일이 아닌 날을 수정 날짜로 입력할 경우 예외를 반환한다.")
    @Test
    void fail_ifModifyDateIsHoliday() {
        //given
        LocalDate holidayDate = LocalDate.of(2024, 12, 14);
        AttendanceHistory attendanceHistory = new AttendanceHistory();

        //when, then
        assertThatThrownBy(() -> {
            attendanceHistory.findByDate(holidayDate);})
                .isInstanceOf(HolidayAttendanceException.class)
                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }

    @DisplayName("수정전 Attendance 객체를 바탕으로 새 Attendance 객체로 교체한다.")
    @Test
    void success_replaceOldWithNewAttendance() {
        //given
        LocalDate modifyDate = LocalDate.of(2024, 12, 13);
        LocalTime modifyTime = LocalTime.of(11, 11);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(modifyDate, LocalTime.of(10, 10));
        Attendance oldAttendance = attendanceHistory.findByDate(modifyDate);

        //when
        Attendance attendance = attendanceHistory.modifyFrom(oldAttendance, modifyTime);

        //then
        assertThat(attendance).isEqualTo(new Attendance(modifyDate, modifyTime));
        assertThat(attendanceHistory.findByDate(modifyDate)).isEqualTo(attendance);
    }

    @DisplayName("미래의 날을 수정 날짜로 입력할 경우 예외를 반환한다.")
    @Test
    void fail_ifModifyAtFutureDate() {
        //given
        LocalDate futureDate = LocalDate.of(2024, 12, 16);
        LocalTime modifyTime = LocalTime.of(11, 11);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        Attendance oldAttendance = attendanceHistory.findByDate(futureDate);

        //when, then
        assertThatThrownBy(() -> {
            attendanceHistory.modifyFrom(oldAttendance, modifyTime);
        }).isInstanceOf(FutureAttendanceException.class);
    }

    /**
     * 출석 기록 조회 관련
     */
    @DisplayName("한 크루의 요청 일자 전날까지의 출석 기록을 반환한다.")
    @Test
    void success_sliceAttendanceDatesUntilBeforeRequestDate() {
        //given
        LocalDate requestDate = LocalDate.of(2024, 12, 13);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0));
        attendanceHistory.register(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6));
        attendanceHistory.register(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31));

        //when
        List<Attendance> attendanceHistories = attendanceHistory.sliceByDateUntilBefore(requestDate);

        //then
        assertThat(attendanceHistories.size()).isEqualTo(9);
        assertThat(attendanceHistories.get(5)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 9), Campus.NONE_ATTENDANCE_TIME));
        assertThat(attendanceHistories.get(6)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 10), LocalTime.of(10, 0)));
        assertThat(attendanceHistories.get(7)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 11), LocalTime.of(10, 6)));
        assertThat(attendanceHistories.get(8)).isEqualTo(
                new Attendance(LocalDate.of(2024, 12, 12), LocalTime.of(10, 31)));
    }


}
