import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceStatus;
import domain.Crew;
import domain.Crews;
import domain.Penalty;
import domain.StatisticsResult;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceStatusTest {

    Crews crews = new Crews();

    @DisplayName("출석 상태를 반환한다.")
    @Test
    void getAttendanceStatus_1() {
        LocalTime time = LocalTime.of(10, 5);
        DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;
        Assertions.assertThat(AttendanceStatus.of(time, dayOfWeek))
            .isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("지각 상태를 반환한다.")
    @Test
    void getAttendanceStatus_2() {
        LocalTime time = LocalTime.of(10, 30);
        DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;
        Assertions.assertThat(AttendanceStatus.of(time, dayOfWeek))
            .isEqualTo(AttendanceStatus.LATENESS);
    }

    @DisplayName("결석 상태를 반환한다.")
    @Test
    void getAttendanceStatus_3() {
        LocalTime time = LocalTime.of(10, 31);
        DayOfWeek dayOfWeek = DayOfWeek.FRIDAY;
        Assertions.assertThat(AttendanceStatus.of(time, dayOfWeek))
            .isEqualTo(AttendanceStatus.ABSENCE);
    }

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
        Crew crew = crews.findCrewByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 7);
        StatisticsResult statisticsResult = AttendanceStatus.countStatus(nowDate, crew);
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
        Crew crew = crews.findCrewByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 7);
        StatisticsResult statisticsResult = AttendanceStatus.countStatus(nowDate, crew);

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
        Crew crew = crews.findCrewByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 10);
        StatisticsResult statisticsResult = AttendanceStatus.countStatus(nowDate, crew);

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
        Crew crew = crews.findCrewByName(name);

        LocalDate nowDate = LocalDate.of(2024, 12, 13);
        StatisticsResult statisticsResult = AttendanceStatus.countStatus(nowDate, crew);

        assertThat(Penalty.EXPELLED).isEqualTo(statisticsResult.getPenalty());
    }
}