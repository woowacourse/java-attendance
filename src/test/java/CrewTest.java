import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.Attendance;
import domain.Crew;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("수정 날짜와 시간을 입력하면 이전 기록은 제거하고 새로운 기록을 추가한다.")
    @Test
    void should_RemovePreviousAndAddNewRecord_When_GivenEditedDateTime() {
        String name = "빙봉";
        LocalDate date = LocalDate.of(2024, 12, 13);
        LocalTime initialTime = LocalTime.of(9, 59);
        Crew crew = new Crew(name, date, initialTime);
        LocalTime updatedTime = LocalTime.of(10, 6);
        Attendance initialAttendance = new Attendance(date, initialTime);
        Attendance updatedAttendance = new Attendance(date, updatedTime);

        crew.updateAttendance(date, updatedTime);
        Attendance actualAttendance = crew.findAttendanceByDate(date);

        assertThat(initialAttendance).isNotEqualTo(actualAttendance);
        assertThat(updatedAttendance).isEqualTo(actualAttendance);
    }

    @DisplayName("전날까지의 출석 횟수를 정확하게 계산한다.")
    @Test
    void should_CalculateAttendanceCount_When_GivenAttendanceRecords() {
        LocalDate nowDate = LocalDate.of(2024, 12, 16);
        String name = "빙티";
        LocalDate initialDate = LocalDate.of(2024, 12, 2);
        LocalTime initialTime = LocalTime.of(13, 6);
        Crew crew = new Crew(name, initialDate, initialTime);

        crew.addAttendance(LocalDate.of(2024, 12, 4), LocalTime.of(10, 2)); // 출석
        crew.addAttendance(LocalDate.of(2024, 12, 6), LocalTime.of(10, 1)); // 출석
        crew.addAttendance(LocalDate.of(2024, 12, 10), LocalTime.of(10, 3)); // 출석
        crew.addAttendance(LocalDate.of(2024, 12, 13), LocalTime.of(10, 2)); // 출석

        assertThat(crew.calculateAttendanceCount(nowDate)).isEqualTo(4);
    }

    @DisplayName("전날까지의 지각 횟수를 정확하게 계산한다.")
    @Test
    void should_CalculateLatenessCount_When_GivenLatenessRecords() {
        LocalDate nowDate = LocalDate.of(2024, 12, 16);
        String name = "빙티";
        LocalDate initialDate = LocalDate.of(2024, 12, 2);
        LocalTime initialTime = LocalTime.of(13, 0);
        Crew crew = new Crew(name, initialDate, initialTime);

        crew.addAttendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 7)); // 지각
        crew.addAttendance(LocalDate.of(2024, 12, 5), LocalTime.of(10, 6)); // 지각

        assertThat(crew.calculateLatenessCount(nowDate)).isEqualTo(2);
    }

    @DisplayName("전날까지의 결석 횟수를 정확하게 계산한다.")
    @Test
    void should_CalculateAbsenceCount_When_GivenAbsenceRecords() {
        LocalDate nowDate = LocalDate.of(2024, 12, 5);
        String name = "빙티";
        LocalDate initialDate = LocalDate.of(2024, 12, 2);
        LocalTime initialTime = LocalTime.of(13, 0);
        Crew crew = new Crew(name, initialDate, initialTime);

        crew.addAttendance(LocalDate.of(2024, 12, 3), LocalTime.of(10, 31)); // 결석

        assertThat(crew.calculateAbsenceCount(nowDate)).isEqualTo(2);
    }

    @DisplayName("전날까지의 크루 출석 기록을 바탕으로 제적 위험자를 파악한다.")
    @Test
    void should_() {
        LocalDate nowDate = LocalDate.of(2024, 12, 13);
    }
}
