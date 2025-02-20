package domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AllCrewTest {
    @DisplayName("모든 제적 위험자 조회 결과 출력")
    @Test
    void test7() {
        AllCrew allCrew = new AllCrew();
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");

        allCrew.addCrew(crew1);
        allCrew.addCrew(crew2);


        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 2, 13,0));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9,58));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10,2));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10,1));  //  출석
        // 9일 결석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 10, 10,8)); // 지각
        // 11, 12 결석
        // 결석 3회, 지각 2회


        // 2 => 결석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10,7)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10,8)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10,29)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10,31)); // 출석 (월요일)
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10,2));//출석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 11, 10,1));//출석
        // 12 => 결석
        // 12일까지 : 2결석 4지각


        // 결과
        assertThat(allCrew.printAllCrewWarningInfo(LocalDate.of(2024, 12, 12)))
                .isEqualTo("""
                        - 빙티: 결석 3회, 지각 2회 (면담)
                        - 이든: 결석 2회, 지각 4회 (면담)
                        """);
    }

    @DisplayName("모든 제적 위험자 조회 결과 출력2 - 정렬")
    @Test
    void test8() {
        AllCrew allCrew = new AllCrew();
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");

        allCrew.addCrew(crew2);
        allCrew.addCrew(crew1);


        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 2, 13,0));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 3, 9,58));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 4, 10,2));  // 출석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 5, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 6, 10,1));  //  출석
        // 9일 결석
        allCrew.addCrewAttendanceByName("빙티", LocalDateTime.of(2024, 12, 10, 10,8)); // 지각
        // 11, 12 결석
        // 결석 3회, 지각 2회


        // 2 => 결석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 3, 10,7)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 4, 10,8)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 5, 10,29)); // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 6, 10,6));  // 지각
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 9, 10,31)); // 출석 (월요일)
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 10, 10,2));//출석
        allCrew.addCrewAttendanceByName("이든", LocalDateTime.of(2024, 12, 11, 10,1));//출석
        // 12 => 결석
        // 12일까지 : 2결석 4지각


        // 결과
        assertThat(allCrew.printAllCrewWarningInfo(LocalDate.of(2024, 12, 12)))
                .isEqualTo("""
                        - 빙티: 결석 3회, 지각 2회 (면담)
                        - 이든: 결석 2회, 지각 4회 (면담)
                        """);
    }
}