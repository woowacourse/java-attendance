package service;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import domain.CrewAttendances;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceHistoryServiceTest {
    LocalDateTime now = AttendanceCustomDate.now();
    int year = now.getYear();
    int month = now.getMonthValue();
    String name = "빙티";

    Map<LocalDate, Attendance> attendanceHistoryResult = new HashMap<>() {{
        put(LocalDate.of(year, month, 2), Attendance.of(LocalDate.of(year, month, 2), LocalTime.of(13, 0)));
        put(LocalDate.of(year, month, 3), Attendance.of(LocalDate.of(year, month, 3), LocalTime.of(10, 7)));
        put(LocalDate.of(year, month, 4), Attendance.of(LocalDate.of(year, month, 4), LocalTime.of(10, 31)));
        put(LocalDate.of(year, month, 5), Attendance.empty(LocalDate.of(year, month, 5)));
    }};

    CrewAttendances crewAttendances;
    AttendanceHistoryService attendanceHistoryService;

    @BeforeEach
    void setUp() {
        crewAttendances = new CrewAttendances();
        crewAttendances.save(new Crew(name));

        crewAttendances.createNewAttendance(name, now.withDayOfMonth(2).toLocalDate(), LocalTime.of(13, 0));
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(3).toLocalDate(), LocalTime.of(10, 7));
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(4).toLocalDate(), LocalTime.of(10, 31));

        attendanceHistoryService = new AttendanceHistoryService(crewAttendances);
    }

    @DisplayName("크루 이름을 입력하면, 해당 크루의 전날까지의 출석 기록을 조회할 수 있다.")
    @Test
    void test1() {
        // given
        LocalDate date = LocalDate.of(year, month, 6);

        // when
        Map<LocalDate, Attendance> histories = attendanceHistoryService.getHistoriesOf(name, date.withDayOfMonth(1), date);

        // then
        assertThat(histories).isEqualTo(attendanceHistoryResult);
    }

    @DisplayName("존재하지 않는 크루의 경우 예외를 발생시킨다.")
    @Test
    void test2() {
        // given


        // when

        // then
    }

    @DisplayName("크루 이름을 입력하면, 전날까지의 출석/지각/결석 횟수를 계산할 수 있다.")
    @Test
    void test3() {
        // given
        LocalDate date = LocalDate.of(year, month, 6);

        // when
        Map<AttendanceStatus, Integer> attendanceCount = attendanceHistoryService.getAttendanceResultOf(name, date.withDayOfMonth(1), date);

        // then
        assertThat(attendanceCount.get(AttendanceStatus.ATTENDANCE)).isEqualTo(1);
        assertThat(attendanceCount.get(AttendanceStatus.LATE)).isEqualTo(1);
        assertThat(attendanceCount.get(AttendanceStatus.ABSENCE)).isEqualTo(2);
    }

    @DisplayName("출석 상태에 따라서 경고/면담/제적 대상자 여부를 반환할 수 있다.")
    @Test
    void test4() {
        // given
        LocalDate date = LocalDate.of(year, month, 6);

        // when
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, date.withDayOfMonth(1), date);

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.WARNING);
    }

    @DisplayName("출석 상태 중 지각 횟수 3회당 결석 1회로 치환해서 대상자 여부를 판단한다.")
    @Test
    void test5() {
        //given
        LocalTime lateTime = LocalTime.of(10, 6);
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(5).toLocalDate(), lateTime);
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(6).toLocalDate(), lateTime);
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(9).toLocalDate(), lateTime);
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(10).toLocalDate(), lateTime);
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(11).toLocalDate(), lateTime);
        crewAttendances.createNewAttendance(name, now.withDayOfMonth(12).toLocalDate(), lateTime);

        LocalDate date = LocalDate.of(year, month, 13);

        //when
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, date.withDayOfMonth(1), date);

        //then
        assertThat(crewStatus).isSameAs(CrewStatus.CONSULTANT);
    }

    @DisplayName("출석 상태 중 결석 횟수가 5회 초과 시 제적 대상자로 판단한다.")
    @Test
    void test6() {
        //given
        LocalDate date = LocalDate.of(year, month, 12);

        //when
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, date.withDayOfMonth(1), date);

        //then
        assertThat(crewStatus).isSameAs(CrewStatus.DISENROLLMENT);
    }
}
