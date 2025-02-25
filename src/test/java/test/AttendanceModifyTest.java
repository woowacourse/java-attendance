package test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import exception.FutureAttendanceModifyException;
import exception.HolidayAttendanceException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import model.Attendance;
import model.Attendances;
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
        Attendances attendances = new Attendances(List.of(
                new Attendance(modifyDate, LocalTime.of(10, 10))
        ));

        //when
        Attendance oldAttendance = attendances.findByDate(modifyDate);

        //then
        assertThat(oldAttendance.equals(new Attendance(modifyDate, LocalTime.of(10, 10))));
    }

    @DisplayName("수정전 Attendance 객체를 바탕으로 새 Attendance 객체로 교체한다.")
    @Test
    void test3() {
        //given
        LocalDate modifyDate = LocalDate.of(2024, 12, 13);
        LocalTime modifyTime = LocalTime.of(11, 11);
        Attendance oldAttendance = new Attendance(modifyDate, LocalTime.of(10, 10));
        Attendances attendances = new Attendances(new ArrayList<>(Arrays.asList(
                oldAttendance
        ))); //TODO : new ttendances에 불변 들어가면 안됨! 만드는 거 분리하기

        //when
        Attendance attendance = attendances.modifyFrom(oldAttendance, modifyTime);

        //then
        assertThat(attendance).isEqualTo(new Attendance(modifyDate, modifyTime));
        assertThat(attendances.findByDate(modifyDate)).isEqualTo(attendance);
    }

    @DisplayName("미래의 날을 수정 날짜로 입력할 경우 예외를 반환한다.")
    @Test
    void test4() {
        //given
        LocalDate futureDate = LocalDate.of(2024, 12, 16);
        LocalTime modifyTime = LocalTime.of(11, 11);
        Attendance oldAttendance = new Attendance(futureDate, LocalTime.of(10, 10));
        Attendances attendances = new Attendances(new ArrayList<>(Arrays.asList(
                oldAttendance
        )));

        assertThatThrownBy(() -> {
            attendances.modifyFrom(oldAttendance, modifyTime);
        }).isInstanceOf(FutureAttendanceModifyException.class);
    }

    @DisplayName("등교일이 아닌 날을 수정 날짜로 입력할 경우 예외를 반환한다.")
    @Test
    void test5() {
        //given
        LocalDate holidayDate = LocalDate.of(2024, 12, 14);
        LocalTime modifyTime = LocalTime.of(11, 11);
        Attendance oldAttendance = new Attendance(holidayDate, LocalTime.of(10, 10));
        Attendances attendances = new Attendances(new ArrayList<>(Arrays.asList(
                oldAttendance
        )));

        assertThatThrownBy(() -> {
            attendances.modifyFrom(oldAttendance, modifyTime);})
                .isInstanceOf(HolidayAttendanceException.class)
                .hasMessageContaining("12월 14일 토요일은 등교일이 아닙니다.");
    }
}
