package test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.attendance.Attendance;
import model.attendance.AttendanceStatistic;
import model.admininstration.AttendanceStatistics;
import model.attendance.AttendanceStatus;
import model.attendance.Crew;
import model.admininstration.Crews;
import model.attendance.PenaltyStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendancePenaltyTest {

    /**
     * Disabled : equals 비교가 불가능해 테스트 불가
     * (from 메서드를 밖으로 빼야 가능할듯)
     */
//    @DisplayName("모든 크루의 특정 날짜까지의 출결 통계를 계산한 객체를 반환한다.")
//    @Test
//    void test() {
//        //given
//        LocalDate requestDate = LocalDate.of(2024, 12, 13);
//        Crews crews = Crews.from(List.of("빙티", "이든"));
//        AttendanceBook attendanceBook = AttendanceBook.from(crews);
//        Crew crew1 = crews.findCrewByName("빙티").get();
//        Crew crew2 = crews.findCrewByName("이든").get();
//
//        Map<Crew, List<Attendance>> attendances = attendanceBook.findAllStatisticsUntilBefore(requestDate);
//        List<Attendance> attendances1 = attendances.get(crew1);
//        List<Attendance> attendances2 = attendances.get(crew2);
//
//        //when
//        AttendanceStatistics penaltyStatistics = AttendanceStatistics.from(attendances);
//
//        //then
//        assertThat(penaltyStatistics).isEqualTo(new AttendanceStatistics(Map.of(
//                crew1, AttendanceStatistic.from(attendances1),
//                crew2, AttendanceStatistic.from(attendances2)
//        )));
//    }

    @DisplayName("모든 크루 중 특정 날짜까지의 출결상태가 경고, 면담, 제적인 크루와 출결상태를 반환한다.")
    @Test
    void test1() {
        Crews crews = Crews.from(List.of("빙티", "이든"));
        Crew crew1 = crews.findCrewByName("빙티").get();
        Crew crew2 = crews.findCrewByName("이든").get();

        AttendanceStatistics attendanceStatistics = AttendanceStatistics.from(new HashMap<>(Map.of(
                crew1, List.of(
                        new Attendance(LocalDate.of(2024, 12, 2), LocalTime.of(10, 0)),
                        new Attendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 0)),
                        new Attendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 10)),
                        new Attendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 31))
                ),
                crew2, List.of(
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
//        assertThat(penaltyTargets.get(crew1).getAttendanceCount()).isEqualTo(Map.of(
//                AttendanceStatus.NORMAL, 2,
//                AttendanceStatus.LATE, 2,
//                AttendanceStatus.ABSENCE, 1
//        ));
//        assertThat(penaltyTargets.get(crew1).getPenaltyStatus()).isEqualTo(PenaltyStatus.NONE);

        assertThat(penaltyTargets.get(crew2).getAttendanceCount()).isEqualTo(Map.of(
                AttendanceStatus.NORMAL, 1,
                AttendanceStatus.LATE, 3,
                AttendanceStatus.ABSENCE, 1
        ));
        assertThat(penaltyTargets.get(crew2).getPenaltyStatus()).isEqualTo(PenaltyStatus.WARNING);
    }
}
