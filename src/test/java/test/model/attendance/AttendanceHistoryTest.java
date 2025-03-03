package test.model.attendance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import model.attendance.Attendance;
import model.attendance.AttendanceHistory;
import model.exception.FutureAttendanceException;
import model.exception.HolidayAttendanceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceHistoryTest {

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


}
