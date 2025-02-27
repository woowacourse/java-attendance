package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import model.Attendance;
import model.AttendanceBook;
import model.AttendanceHistory;
import model.AttendanceStatistic;
import model.AttendanceStatistics;
import model.Crew;
import model.Crews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancePenaltyTest {

    @DisplayName("모든 크루의 특정 날짜까지의 AttendanceStatistics 객체를 반환한다.")
    @Test
    void test() {
        //given
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");
        AttendanceBook attendanceBook = AttendanceBook.from(Crews.from(List.of("빙티", "이든")));

        AttendanceHistory crewHistory1 = attendanceBook.findByCrew(crew1);
        AttendanceHistory crewHistory2 = attendanceBook.findByCrew(crew2);

        //when
        AttendanceStatistics penaltyStatistics = attendanceBook.findAllStatistics();

        //then
        assertThat(penaltyStatistics).isEqualTo(new AttendanceStatistics(List.of(
                new AttendanceStatistic(crewHistory1),
                new AttendanceStatistic(crewHistory2)
        )));
    }

    @DisplayName("모든 크루의 특정 날짜까지의 출결상태가 경고, 면담, 제적인 경우를 반환한다.")
    @Test
    void test1() {
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");
        AttendanceBook attendanceBook = AttendanceBook.from(Crews.from(List.of("빙티", "이든")));

        AttendanceHistory crewHistory1 = attendanceBook.findByCrew(crew1); //패널티 없음
        crewHistory1.register(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)); //출석
        crewHistory1.register(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)); //출석
        crewHistory1.register(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)); //지각
        crewHistory1.register(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)); //지각
        crewHistory1.register(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)); //결석

        AttendanceHistory crewHistory2 = attendanceBook.findByCrew(crew2); //경고
        crewHistory2.register(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)); //출석
        crewHistory2.register(LocalDate.of(2024, 12, 3), LocalTime.of(10, 10)); //지각
        crewHistory2.register(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)); //지각
        crewHistory2.register(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)); //지각
        crewHistory2.register(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31)); //결석

        AttendanceStatistics attendanceStatistics = attendanceBook.findAllStatistics();

        //when
        List<AttendanceStatistic> penaltyTargets = attendanceStatistics.findPenaltyTargets();

        //then
        assertThat(penaltyTargets).isEqualTo(List.of(
                crewHistory2
        ));
    }
}
