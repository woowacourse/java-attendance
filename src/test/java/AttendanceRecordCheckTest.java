import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceManager;
import domain.AttendanceStatistics;
import domain.Penalty;
import domain.Crew;
import domain.StatisticsResult;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordCheckTest {

    AttendanceManager attendanceManager = new AttendanceManager();
    List<LocalDateTime> records = new ArrayList<>();

    @BeforeEach
    void setUp() {
        records = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
            LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
            LocalDateTime.of(2024, 12, 6, 10, 40)); // 결석

        String name = "빙티"; // 경고 대상자
        attendanceManager.createCrew(name, records);
    }

    @DisplayName("크루 출석 기록을 전날까지 조회한다.")
    @Test
    void 출석_기록_조회() {
        String name = "빙티";
        Crew expectedCrew = attendanceManager.findByName(name);
        assertThat(expectedCrew.getAttendanceCount()).isEqualTo(records.size());
    }

    @DisplayName("출석 통계를 정확하게 계산한다.")
    @Test
    void 출석_통계_계산() {
        String name = "빙티";
        Crew crew = attendanceManager.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 7);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);
        int attendanceCount = statisticsResult.getAttendanceCount();
        int latenessCount = statisticsResult.getLatenessCount();
        int absenceCount = statisticsResult.getAbsenceCount();

        assertThat(attendanceCount).isEqualTo(2);
        assertThat(latenessCount).isEqualTo(1);
        assertThat(absenceCount).isEqualTo(2);
    }

    @DisplayName("경고 기준을 정확하게 계산한다.")
    @Test
    void 경고_기준_계산() {
        String name = "빙티";
        Crew crew = attendanceManager.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 7);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);

        assertThat(Penalty.WARNING).isEqualTo(statisticsResult.getPenalty());
    }

    @DisplayName("면담 기준을 정확하게 계산한다.")
    @Test
    void 면담_기준_계산() {
        List<LocalDateTime> counselingRecords = List.of(
            LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
            LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
            LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
            LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
            LocalDateTime.of(2024, 12, 6, 10, 40), // 결석
            LocalDateTime.of(2024, 12, 9, 13, 40)); // 결석

        String name = "빙티";
        attendanceManager.createCrew(name, counselingRecords);
        Crew crew = attendanceManager.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 10);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);

        assertThat(Penalty.COUNSELING).isEqualTo(statisticsResult.getPenalty());
    }

    @DisplayName("제적 기준을 정확하게 계산한다.")
    @Test
    void 제적_기준_계산() {
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
        attendanceManager.createCrew(name, expelledRecords);
        Crew crew = attendanceManager.findByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 13);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, crew);

        assertThat(Penalty.EXPELLED).isEqualTo(statisticsResult.getPenalty());
    }
}
