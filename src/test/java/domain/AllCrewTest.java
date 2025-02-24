package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AllCrewTest {

    @DisplayName("결석 내림차순 정렬")
    @Test
    void test1() {
        // given
        AllCrew allCrew = new AllCrew();
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");
        // 이든, 빙티 순으로 삽입
        allCrew.addCrew(crew2);
        allCrew.addCrew(crew1);

        // 2일 결석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9,58));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10,2));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10,1));  //  출석
        // 7일 결석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 10, 10,8)); // 지각
        // 11, 12 결석
        // 결석 4회, 지각 2회 => 4결석

        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 2, 10,31)); // 출석 (월요일)
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10,7)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10,8)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10,29)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10,31)); // 출석 (월요일)
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10,2));//출석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 11, 10,1));//출석
        // 12 일 결석
        // 1결석 4지각 => 2결석
        allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 12));

        // when
        allCrew.sortAllCrewOrderByWarningInfo();

        // then
        assertThat(allCrew.getAllWarningCrew()).extracting(Crew::getName).containsExactly("빙티", "이든");
    }

    @DisplayName("이름 오름차순 정렬")
    @Test
    void test2() {
        // given
        AllCrew allCrew = new AllCrew();
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");
        // 이든, 빙티 순으로 삽입
        allCrew.addCrew(crew2);
        allCrew.addCrew(crew1);
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 2, 13,0));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9,58));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10,2));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10,1));  //  출석
        // 7일 결석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 10, 10,8)); // 지각
        // 11, 12 결석
        // 결석 3회, 지각 2회

        // 2일 결석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10,7)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10,8)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10,29)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10,31)); // 출석 (월요일)
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10,2));//출석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 11, 10,1));//출석
        // 12 일 결석
        // 2결석 4지각
        allCrew.updateAbsentHistory(LocalDate.of(2024, 12, 12));

        // when
        allCrew.sortAllCrewOrderByWarningInfo();

        // then
        assertThat(allCrew.getAllWarningCrew()).extracting(Crew::getName).containsExactly("빙티", "이든");
    }

}