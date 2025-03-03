package test.model.administration;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.admininstration.AttendanceStatistics;
import model.admininstration.Crews;
import model.attendance.Attendance;
import model.attendance.AttendanceStatistic;
import model.attendance.AttendanceStatus;
import model.attendance.Crew;
import model.attendance.PenaltyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatisticsTest {

    @DisplayName("모든 크루 중 특정 날짜까지의 출결상태가 경고, 면담, 제적인 크루와 출결상태를 반환한다.")
    @Test
    void success_findAllPenaltyExistingCrews() {
        Crews crews = Crews.from(List.of("빙티", "이든"));
        Crew crew1 = crews.findCrewByName("빙티").get();
        Crew crew2 = crews.findCrewByName("이든").get();

        AttendanceStatistics attendanceStatistics = AttendanceStatistics.from(new HashMap<>(Map.of(
                crew1, List.of( //none_penalty
                        new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31))
                ),
                crew2, List.of( //warning
                        new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)),
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31))
                )
        )));

        //when
        Map<Crew, AttendanceStatistic> penaltyTargets = attendanceStatistics.findPenaltyTargets();

        //then
        assertThat(penaltyTargets.keySet().size()).isEqualTo(1);
        assertThat(penaltyTargets.get(crew2).getAttendanceCount()).isEqualTo(Map.of(
                AttendanceStatus.NORMAL, 1,
                AttendanceStatus.LATE, 3,
                AttendanceStatus.ABSENCE, 1
        ));
        assertThat(penaltyTargets.get(crew2).getPenaltyStatus()).isEqualTo(PenaltyStatus.WARNING);
    }
}
