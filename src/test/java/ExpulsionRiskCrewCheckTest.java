import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceBook;
import domain.Crew;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ExpulsionRiskCrewCheckTest {

    AttendanceBook attendanceBook = new AttendanceBook();

    @DisplayName("전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.")
    @Test
    void should_IdentifyExpulsionRiskCrew_When_GivenAttendanceRecords() {
        LocalDate nowDate = LocalDate.of(2024, 12, 13);

        assertThat(attendanceBook.checkExpulsionRiskCrew(nowDate).size()).isEqualTo(5);
    }

    @DisplayName("제적 대상자, 면담 대상자, 경고 대상자순으로 정렬한다.")
    @Test
    void should_SortRiskCrews_ByPenaltyStatus() {
        LocalDate nowDate = LocalDate.of(2024, 12, 13);

        List<Crew> riskCrewResult = attendanceBook.checkExpulsionRiskCrew(nowDate);

        assertThat(riskCrewResult.getFirst().determinePenaltyStatus(nowDate)).isSameAs(Penalty.COUNSEL);
        assertThat(riskCrewResult.get(1).determinePenaltyStatus(nowDate)).isSameAs(Penalty.COUNSEL);
        assertThat(riskCrewResult.get(2).determinePenaltyStatus(nowDate)).isSameAs(Penalty.COUNSEL);
        assertThat(riskCrewResult.get(3).determinePenaltyStatus(nowDate)).isSameAs(Penalty.COUNSEL);
        assertThat(riskCrewResult.get(4).determinePenaltyStatus(nowDate)).isSameAs(Penalty.WARNING);
    }

    @DisplayName("대상 항목별 정렬 순서는 지각 3회를 결석 1회로 간주하여 내림차순한다.")
    @Test
    void should_SortRiskCrews_ByAdjustedAbsences() {
        LocalDate nowDate = LocalDate.of(2024, 12, 13);

        List<Crew> riskCrewResult = attendanceBook.checkExpulsionRiskCrew(nowDate);
        Crew firstRankedCrew = riskCrewResult.getFirst();
        Crew secondRankedCrew = riskCrewResult.get(1);
        Crew thirdRankedCrew = riskCrewResult.get(2);
        Crew fourthRankedCrew = riskCrewResult.get(3);
        Crew fifthRankedCrew = riskCrewResult.get(4);

        assertThat(firstRankedCrew.calculateAbsenceCount(nowDate)).isEqualTo(3);
        assertThat(firstRankedCrew.calculateLatenessCount(nowDate)).isEqualTo(4);

        assertThat(secondRankedCrew.calculateAbsenceCount(nowDate)).isEqualTo(2);
        assertThat(secondRankedCrew.calculateLatenessCount(nowDate)).isEqualTo(5);

        assertThat(thirdRankedCrew.calculateAbsenceCount(nowDate)).isEqualTo(1);
        assertThat(thirdRankedCrew.calculateLatenessCount(nowDate)).isEqualTo(6);

        assertThat(fourthRankedCrew.calculateAbsenceCount(nowDate)).isEqualTo(2);
        assertThat(fourthRankedCrew.calculateLatenessCount(nowDate)).isEqualTo(3);

        assertThat(fifthRankedCrew.calculateAbsenceCount(nowDate)).isEqualTo(0);
        assertThat(fifthRankedCrew.calculateLatenessCount(nowDate)).isEqualTo(6);
    }

    @DisplayName("출석 상태가 같으면 닉네임으로 오름차순 정렬한다.")
    @Test
    void should_SortRiskCrews_ByName_When_SamePenaltyStatus() {
        LocalDate nowDate = LocalDate.of(2024, 12, 13);

        List<Crew> riskCrewResult = attendanceBook.checkExpulsionRiskCrew(nowDate);
        Crew thirdRankedCrew = riskCrewResult.get(2);
        Crew fourthRankedCrew = riskCrewResult.get(3);

        assertThat(thirdRankedCrew.getName()).isEqualTo("빙봉");
        assertThat(fourthRankedCrew.getName()).isEqualTo("쿠키");
    }

    @BeforeEach
    void initialCrew() {
        String name1 = "빙티"; // 면담
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 2), LocalTime.of(13, 6)); // 지각
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)); // 지각
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 4), LocalTime.of(10, 8)); // 지각
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 5), LocalTime.of(10, 29)); // 지각
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)); // 출석
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 9), LocalTime.of(13, 4)); // 출석
        attendanceBook.addCrew(name1, LocalDate.of(2024, 12, 10), LocalTime.of(10, 31)); // 결석
        // 2024-12-11 결석
        // 2024-12-12 결석

        String name2 = "이든"; // 면담
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 2), LocalTime.of(13, 6)); // 지각
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)); // 지각
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 4), LocalTime.of(10, 6)); // 지각
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 5), LocalTime.of(10, 29)); // 지각
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 6), LocalTime.of(10, 12)); // 지각
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 9), LocalTime.of(13, 4)); // 출석
        attendanceBook.addCrew(name2, LocalDate.of(2024, 12, 10), LocalTime.of(10, 4)); // 출석
        // 2024-12-11 결석
        // 2024-12-12 결석

        String name3 = "빙봉"; // 면
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 2), LocalTime.of(13, 6)); // 지각
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)); // 지각
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 4), LocalTime.of(10, 11)); // 지각
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 5), LocalTime.of(10, 12)); // 지각
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 6), LocalTime.of(10, 13)); // 지각
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 9), LocalTime.of(13, 24)); // 지각
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 10), LocalTime.of(10, 4)); // 출석
        attendanceBook.addCrew(name3, LocalDate.of(2024, 12, 11), LocalTime.of(10, 4)); // 출석
        // 2024-12-12 결석

        String name4 = "쿠키"; // 면담
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 2), LocalTime.of(13, 6)); // 지각
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)); // 지각
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 4), LocalTime.of(10, 13)); // 지각
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 5), LocalTime.of(10, 2)); // 출석
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)); // 출석
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 9), LocalTime.of(13, 4)); // 출석
        attendanceBook.addCrew(name4, LocalDate.of(2024, 12, 10), LocalTime.of(10, 4)); // 출석
        // 2024-12-11 결석
        // 2024-12-12 결석

        String name5 = "짱수"; // 경고
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 2), LocalTime.of(13, 6)); // 지각
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 3), LocalTime.of(10, 17)); // 지각
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 4), LocalTime.of(10, 13)); // 지각
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 5), LocalTime.of(10, 12)); // 지각
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 6), LocalTime.of(10, 11)); // 지각
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 9), LocalTime.of(13, 14)); // 지각
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 10), LocalTime.of(10, 4)); // 출석
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 11), LocalTime.of(10, 4)); // 출석
        attendanceBook.addCrew(name5, LocalDate.of(2024, 12, 12), LocalTime.of(10, 4)); // 출석
    }
}
