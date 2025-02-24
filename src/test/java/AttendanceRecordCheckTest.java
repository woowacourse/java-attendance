import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceManager;
import domain.AttendanceStatistics;
import domain.Penalty;
import domain.Records;
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
        String name = "빙티"; // 경고 대상자
        records = List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
                LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
                LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
                LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
                LocalDateTime.of(2024, 12, 6, 10, 40) // 결석
        );

        attendanceManager.createCrew(name, records);
    }

    @Test
    @DisplayName("크루 출석 기록을 전날까지 조회한다.")
    void should_CheckAttendanceRecords_When_SearchingByName() {
        String name = "빙티";

        Records expectedRecords = attendanceManager.findByName(name);

        assertThat(expectedRecords.getAttendanceCount()).isEqualTo(records.size());
    }

    @Test
    @DisplayName("출석 통계를 정확하게 계산한다.")
    void should_CalculateAttendanceStatistics_When_GivenRecords() {
        String name = "빙티";
        LocalDate nowDate = LocalDate.of(2024, 12, 8);

        Records records = attendanceManager.findByName(name);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, records);

        assertThat(statisticsResult.getAttendanceCount()).isEqualTo(2);
        assertThat(statisticsResult.getLatenessCount()).isEqualTo(1);
        assertThat(statisticsResult.getAbsenceCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("경고 기준을 정확하게 계산한다.")
    void should_CalculateWarningStatistics_When_GivenRecords() {
        String name = "빙티";
        LocalDate nowDate = LocalDate.of(2024, 12, 7);

        Records records = attendanceManager.findByName(name);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, records);

        assertThat(Penalty.WARNING).isEqualTo(statisticsResult.getPenalty());
    }

    @Test
    @DisplayName("면담 기준을 정확하게 계산한다.")
    void should_CalculateCounselingStatistics_When_GivenRecords() {
        String name = "빙티";
        List<LocalDateTime> counselingRecords = List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
                LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
                LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
                LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
                LocalDateTime.of(2024, 12, 6, 10, 40), // 결석
                LocalDateTime.of(2024, 12, 9, 13, 40) // 결석
        );
        LocalDate nowDate = LocalDate.of(2024, 12, 10);

        attendanceManager.createCrew(name, counselingRecords);
        Records records = attendanceManager.findByName(name);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, records);

        assertThat(Penalty.COUNSELING).isEqualTo(statisticsResult.getPenalty());
    }

    @DisplayName("제적 기준을 정확하게 계산한다.")
    @Test
    void should_CalculateExpelledStatistics_When_GivenRecords() {
        String name = "빙티";
        List<LocalDateTime> expelledRecords = List.of(
                LocalDateTime.of(2024, 12, 2, 13, 0), // 출석
                LocalDateTime.of(2024, 12, 3, 9, 59), // 출석
                LocalDateTime.of(2024, 12, 4, 10, 6), // 지각
                LocalDateTime.of(2024, 12, 5, 10, 31),// 결석
                LocalDateTime.of(2024, 12, 6, 10, 40), // 결석
                LocalDateTime.of(2024, 12, 9, 13, 40), // 결석
                LocalDateTime.of(2024, 12, 10, 11, 40), // 결석
                LocalDateTime.of(2024, 12, 11, 11, 40), // 결석
                LocalDateTime.of(2024, 12, 12, 11, 40) // 결석
        );
        LocalDate nowDate = LocalDate.of(2024, 12, 13);

        attendanceManager.createCrew(name, expelledRecords);
        Records records = attendanceManager.findByName(name);
        StatisticsResult statisticsResult = AttendanceStatistics.countStatus(nowDate, records);

        assertThat(Penalty.EXPELLED).isEqualTo(statisticsResult.getPenalty());
    }
}
