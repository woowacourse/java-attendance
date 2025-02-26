package service;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceModifyServiceTest {
    String name = "빙티";
    Crew crew = new Crew(name);
    CrewAttendances crewAttendances;

    @BeforeEach
    void setUp() {
        crewAttendances = new CrewAttendances();
        crewAttendances.registerCrew(crew);
    }

    @DisplayName("기존 출석 기록을 수정한다.")
    @Test
    void test1() {
        //given
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 19);
        LocalTime beforeTime = LocalTime.of(10, 30);
        LocalTime afterTime = LocalTime.of(10, 0);
        crewAttendances.createNewAttendance(name, date, beforeTime);

        //when
        Attendance modifiedAttendance = crewAttendances.modifyAttendance(name, date, afterTime);

        //then
        assertThat(modifiedAttendance.getTime()).isEqualTo(Optional.of(afterTime));
        assertThat(modifiedAttendance.getStatus()).isSameAs(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("출석 기록이 존재하지 않는 경우에도 수정할 수 있다.")
    @Test
    void test2() {
        //given
        LocalDate date = LocalDate.of(AttendanceCustomDate.YEAR, AttendanceCustomDate.MONTH.getValue(), 19);
        LocalTime afterTime = LocalTime.of(10, 0);

        //when
        Attendance modifiedAttendance = crewAttendances.modifyAttendance(name, date, afterTime);

        //then
        assertThat(modifiedAttendance.getTime()).isEqualTo(Optional.of(afterTime));
        assertThat(modifiedAttendance.getStatus()).isSameAs(AttendanceStatus.ATTENDANCE);
    }
}
