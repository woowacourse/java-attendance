import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceBook;
import domain.Penalty;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceRecordCheckTest {

    private AttendanceBook attendanceBook;

    @BeforeEach
    void setUp() {
        attendanceBook = new AttendanceBook();

        String name = "빙티";
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 2), LocalTime.of(13, 0)); //출석
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)); // 지각
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)); // 출석
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 5), LocalTime.of(10, 6)); // 지각
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)); // 출석
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 10), LocalTime.of(10, 3)); // 출석
        attendanceBook.addCrew(name, LocalDate.of(2024, 12, 13), LocalTime.of(10, 2)); // 출석
    }

    @DisplayName("출석 통계를 정확하게 계산한다.")
    @Test
    void should_CalculateAttendanceStatistics_When_GivenAttendanceRecords() {
        String name = "빙티";
        LocalDate nowDate = LocalDate.of(2024, 12, 16);

        assertThat(attendanceBook.calculateAttendanceCount(name, nowDate)).isEqualTo(5);
        assertThat(attendanceBook.calculateLatenessCount(name, nowDate)).isEqualTo(2);
        assertThat(attendanceBook.calculateAbsenceCount(name, nowDate)).isEqualTo(3);
    }

    @DisplayName("경고, 면담, 제적에 해당하는 경우 상태를 정확하게 판단한다.")
    @Test
    void should_DeterminePenaltyStatus_When_PenaltyApplies() {
        String name = "빙티";
        LocalDate nowDate = LocalDate.of(2024, 12, 16);

        assertThat(attendanceBook.determinePenaltyStatus(name, nowDate)).isSameAs(Penalty.COUNSEL);
    }
}

