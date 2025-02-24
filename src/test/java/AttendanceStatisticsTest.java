import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Crews;
import domain.AttendanceStatistics;
import domain.AttendanceStatus;
import domain.Crew;
import domain.Penalty;
import domain.StatisticsResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatisticsTest {

    Crews crews = new Crews();

    @DisplayName("출석 통계를 정확하게 계산한다.")
    @Test
    void countStatusTest_1() {
        List<LocalDateTime> records = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
            LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
            LocalDateTime.of(2024, 12, 6, 10, 40)); // 결석

        String name = "빙티"; // 경고 대상자
        crews.createCrew(name, records);
        Crew crew = crews.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 7);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);
        int attendanceCount = statisticsResult.getCount(AttendanceStatus.ATTENDANCE);
        int latenessCount = statisticsResult.getCount(AttendanceStatus.LATENESS);
        int absenceCount = statisticsResult.getCount(AttendanceStatus.ABSENCE);

        assertThat(attendanceCount).isEqualTo(2);
        assertThat(latenessCount).isEqualTo(1);
        assertThat(absenceCount).isEqualTo(2);
    }

    @DisplayName("경고 기준을 정확하게 계산한다.")
    @Test
    void countStatusTest_2() {
        List<LocalDateTime> records = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
            LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
            LocalDateTime.of(2024, 12, 6, 10, 40)); // 결석

        String name = "빙티"; // 경고 대상자
        crews.createCrew(name, records);
        Crew crew = crews.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 7);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);

        assertThat(Penalty.WARNING).isEqualTo(statisticsResult.getPenalty());
    }

    @DisplayName("면담 기준을 정확하게 계산한다.")
    @Test
    void countStatusTest_3() {
        List<LocalDateTime> counselingRecords = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
            LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
            LocalDateTime.of(2024, 12, 6, 10, 40), // 결석
            LocalDateTime.of(2024, 12, 9, 13, 40)); // 결석

        String name = "빙티";
        crews.createCrew(name, counselingRecords);
        Crew crew = crews.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 10);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);

        assertThat(Penalty.COUNSELING).isEqualTo(statisticsResult.getPenalty());
    }

    @DisplayName("제적 기준을 정확하게 계산한다.")
    @Test
    void countStatusTest_4() {
        List<LocalDateTime> expelledRecords = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
            LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
            LocalDateTime.of(2024, 12, 6, 10, 40), // 결석
            LocalDateTime.of(2024, 12, 9, 13, 40), // 결석
            LocalDateTime.of(2024, 12, 10, 11, 40), // 결석
            LocalDateTime.of(2024, 12, 11, 11, 40), // 결석
            LocalDateTime.of(2024, 12, 12, 11, 40)); // 결석

        String name = "빙티";
        crews.createCrew(name, expelledRecords);
        Crew crew = crews.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 13);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);

        assertThat(Penalty.EXPELLED).isEqualTo(statisticsResult.getPenalty());
    }

    @DisplayName("크루 출석 기록을 바탕으로 제적 위험자를 파악한다.")
    @Test
    void calculateExpelledWarning_1() {
        List<LocalDateTime> testRecords1 = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 6), // 지각
            LocalDateTime.of(2024, 12, 3, 9, 7), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 8), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 9),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 10),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 40),// 결석
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        List<LocalDateTime> testRecords2 = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 8), // 지각
            LocalDateTime.of(2024, 12, 3, 10, 5), // 지각
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 7),// 지각
            LocalDateTime.of(2024, 12, 6, 10, 6),// 지각
            LocalDateTime.of(2024, 12, 9, 13, 6),// 지각
            LocalDateTime.of(2024, 12, 10, 10, 40) // 결석
        );

        crews.createCrew("이든", testRecords1); // 면담 대상자
        crews.createCrew("빙봉", testRecords2); // 면담 대상자
        LocalDate nowDate = LocalDate.of(2024, 12, 11);
        Map<String, StatisticsResult> warningCrews = crews.findWarningCrews(nowDate);

        assertThat(warningCrews.size()).isEqualTo(2);
    }
}
