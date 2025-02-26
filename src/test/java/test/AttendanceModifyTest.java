package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.FutureAttendanceModifyException;
import exception.HolidayAttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import model.Attendance;
import model.AttendanceHistory;
import model.DateGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceModifyTest {

    @DisplayName("수정하려는 날짜를 LocalDate 객체로 반환한다.")
    @Test
    void test() { //TODO : 좀 필요없는 테스트임 (a=a 테스트)
        //given
        int rawDate = 3;

        //when
        LocalDate date = DateGenerator.create(rawDate);

        //then
        assertThat(date).isEqualTo(LocalDate.of(2024, 12, rawDate));
    }

    //TODO : 이미 있는 메서드였음
    @DisplayName("수정하려는 날짜에 맞는 수정전 Attendance 객체를 찾는다.")
    @Test
    void test1() {
        //given
        LocalDate modifyDate = LocalDate.of(2024, 12, 13);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(modifyDate, LocalTime.of(10, 10));

        //when
        Attendance oldAttendance = attendanceHistory.findByDate(modifyDate);

        //then
        assertThat(oldAttendance).isEqualTo(new Attendance(modifyDate, LocalTime.of(10, 10)));
    }

    @DisplayName("수정전 Attendance 객체를 바탕으로 새 Attendance 객체로 교체한다.")
    @Test
    void test3() {
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
    void test4() {
        //given
        LocalDate futureDate = LocalDate.of(2024, 12, 16);
        LocalTime modifyTime = LocalTime.of(11, 11);
        AttendanceHistory attendanceHistory = new AttendanceHistory();
        attendanceHistory.register(futureDate, LocalTime.of(10, 10));
        Attendance oldAttendance = attendanceHistory.findByDate(futureDate);

        //when, then
        assertThatThrownBy(() -> {
            attendanceHistory.modifyFrom(oldAttendance, modifyTime);
        }).isInstanceOf(FutureAttendanceModifyException.class);
    }

    /**
     * TODO : 아예 수정날짜까지 등교일을 검사할 필요 없음 -> 한단계 안쪽인 Attenacne에서 처리하는 방향이 좋을듯
     */
//    @DisplayName("등교일이 아닌 날을 수정 날짜로 입력할 경우 예외를 반환한다.")
//    @Test
//    void test5() {
//        //given
//        LocalDate holidayDate = LocalDate.of(2024, 12, 14);
//        LocalTime modifyTime = LocalTime.of(11, 11);
//        AttendanceHistory attendanceHistory = new AttendanceHistory();
//        attendanceHistory.register(holidayDate, LocalTime.of(10, 10));
//        Attendance oldAttendance = attendanceHistory.findByDate(holidayDate);
//
//        //when, then
//        assertThatThrownBy(() -> {
//            attendanceHistory.modifyFrom(oldAttendance, modifyTime);})
//                .isInstanceOf(HolidayAttendanceException.class)
//                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
//    }
}
