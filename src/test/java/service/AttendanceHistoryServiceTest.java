package service;

import domain.attendance.AttendanceStatus;
import domain.crew.Crew;
import domain.crew.CrewStatus;
import domain.date.CustomDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import service.dto.AttendanceHistoryResponse;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class AttendanceHistoryServiceTest {
    int year = CustomDate.YEAR;
    int month = CustomDate.MONTH.getValue();
    String name = "빙티";
    Crew crew = new Crew(name);
    List<AttendanceHistoryResponse> attendanceHistoryResponses = new ArrayList<>(Arrays.asList(
            new AttendanceHistoryResponse(LocalDate.of(year, month, 2), Optional.of(LocalTime.of(13, 0)), AttendanceStatus.ATTENDANCE),
            new AttendanceHistoryResponse(LocalDate.of(year, month, 3), Optional.of(LocalTime.of(10, 7)), AttendanceStatus.LATE),
            new AttendanceHistoryResponse(LocalDate.of(year, month, 4), Optional.of(LocalTime.of(10, 31)), AttendanceStatus.ABSENCE),
            new AttendanceHistoryResponse(LocalDate.of(year, month, 5), Optional.empty(), AttendanceStatus.ABSENCE)
    ));
    AttendanceRepository attendanceRepository;
    AttendanceHistoryService attendanceHistoryService;

    @BeforeEach
    void setUp() {
        attendanceRepository = new AttendanceRepositoryImpl();
        attendanceRepository.save(crew);
        attendanceRepository.createNewAttendance(name, 2, 13, 0);
        attendanceRepository.createNewAttendance(name, 3, 10, 7);
        attendanceRepository.createNewAttendance(name, 4, 10, 31);
        attendanceHistoryService = new AttendanceHistoryService(attendanceRepository);
    }

    @DisplayName("크루 이름을 입력하면, 해당 크루의 전날까지의 출석 기록을 조회할 수 있다.")
    @Test
    void test1() {
        // given
        LocalDate date = LocalDate.of(year, month, 6);

        // when
        List<AttendanceHistoryResponse> responses = attendanceHistoryService.getHistoriesOf(name, date);

        // then
        assertThat(responses).containsExactlyElementsOf(attendanceHistoryResponses);
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
        Map<AttendanceStatus, Integer> attendanceCount = attendanceHistoryService.getAttendanceResultOf(name, date);

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
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, date);

        // then
        assertThat(crewStatus).isSameAs(CrewStatus.WARNING);
    }

    @DisplayName("출석 상태 중 지각 횟수 3회당 결석 1회로 치환해서 대상자 여부를 판단한다.")
    @Test
    void test5() {
        //given
        attendanceRepository.createNewAttendance(name, 5, 10, 6);
        attendanceRepository.createNewAttendance(name, 6, 10, 6);
        attendanceRepository.createNewAttendance(name, 9, 13, 6);
        attendanceRepository.createNewAttendance(name, 10, 10, 6);
        attendanceRepository.createNewAttendance(name, 11, 10, 6);
        attendanceRepository.createNewAttendance(name, 12, 10, 6);

        LocalDate date = LocalDate.of(year, month, 13);

        //when
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, date);

        //then
        assertThat(crewStatus).isSameAs(CrewStatus.CONSULTANT);
    }

    @DisplayName("출석 상태 중 결석 횟수가 5회 초과 시 제적 대상자로 판단한다.")
    @Test
    void test6() {
        //given
        LocalDate date = LocalDate.of(year, month, 12);

        //when
        CrewStatus crewStatus = attendanceHistoryService.getCrewStatus(name, date);

        //then
        assertThat(crewStatus).isSameAs(CrewStatus.DISENROLLMENT);
    }
}
