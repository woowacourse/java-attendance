package service;

import constants.DateConstants;
import domain.Crew;
import domain.CrewStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;
import repository.AttendanceRepositoryImpl;
import service.dto.AttendanceHistoryResponse;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceHistoryServiceTest {
    int year = DateConstants.YEAR;
    int month = DateConstants.MONTH.getValue();
    String name = "빙티";
    Crew crew = new Crew(name);
    List<AttendanceHistoryResponse> attendanceHistoryResponses = List.of(
            new AttendanceHistoryResponse(LocalDate.of(year, month, 2), Optional.of(LocalTime.of(13, 0)), "출석"),
            new AttendanceHistoryResponse(LocalDate.of(year, month, 3), Optional.of(LocalTime.of(10, 7)), "지각"),
            new AttendanceHistoryResponse(LocalDate.of(year, month, 4), Optional.of(LocalTime.of(10, 31)), "결석"),
            new AttendanceHistoryResponse(LocalDate.of(year, month, 5), Optional.empty(), "결석")
    );
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
        Map<String, Integer> attendanceCount = attendanceHistoryService.getAttendanceResultOf(name, date);

        // then
        assertThat(attendanceCount.get("출석")).isEqualTo(1);
        assertThat(attendanceCount.get("지각")).isEqualTo(1);
        assertThat(attendanceCount.get("결석")).isEqualTo(2);

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
}
