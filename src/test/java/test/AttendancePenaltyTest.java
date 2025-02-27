package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import model.AttendanceBook;
import model.AttendanceHistory;
import model.AttendanceStatistics;
import model.Crew;
import model.Crews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancePenaltyTest {

    @DisplayName("모든 크루의 특정 날짜까지의 AttendanceStatistics 객체를 반환한다.")
    @Test
    void test() {
        Crew crew1 = new Crew("빙티");
        Crew crew2 = new Crew("이든");
        AttendanceBook attendanceBook = AttendanceBook.from(Crews.from(List.of("빙티", "이든")));

        AttendanceHistory crewHistory1 = attendanceBook.findByCrew(crew1);
        AttendanceHistory crewHistory2 = attendanceBook.findByCrew(crew2);

        List<AttendanceStatistics> penaltyStatistics = attendanceBook.findAllStatistics();

        assertThat(penaltyStatistics.size()).isEqualTo(2);
        assertThat(penaltyStatistics.get(0)).isEqualTo(new AttendanceStatistics(crewHistory1));
        assertThat(penaltyStatistics.get(1)).isEqualTo(new AttendanceStatistics(crewHistory2));
    }
}
