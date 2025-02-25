import static org.assertj.core.api.Assertions.assertThat;

import domain.AttendanceStatus;
import domain.Crew;
import domain.DailyRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CrewTest {

    @DisplayName("날짜에 해당하는 출석 기록을 못찾는다.")
    @Test
    void NotFindRecordByDate_1() {
        Crew crew = new Crew(List.of(
            LocalDateTime.of(2024, 12, 13, 10, 3)
            , LocalDateTime.of(2024, 12, 14, 9, 30)));

        LocalDate date = LocalDate.of(2024, 12, 15);

        Optional<DailyRecord> result = crew.findRecordByDate(date);

        assertThat(result).isEmpty();
    }

    @DisplayName("날짜에 해당하는 출석 기록을 찾는다.")
    @Test
    void findRecordByDate_1() {
        Crew crew = new Crew(List.of(
            LocalDateTime.of(2024, 12, 13, 10, 3)
            , LocalDateTime.of(2024, 12, 14, 9, 30)));

        LocalDate date = LocalDate.of(2024, 12, 14);
        LocalTime expectedTime = LocalTime.of(9, 30);

        Optional<DailyRecord> result = crew.findRecordByDate(date);
        assertThat(result.get().getTime()).isEqualTo(expectedTime);
    }

    @DisplayName("날짜에 해당하는 출석 기록의 상태를 찾는다.")
    @Test
    void findStatusByDate_1() {
        Crew crew = new Crew(List.of(
            LocalDateTime.of(2024, 12, 13, 10, 3)
            , LocalDateTime.of(2024, 12, 14, 9, 30)));

        LocalDate date = LocalDate.of(2024, 12, 14);

        AttendanceStatus status = crew.findStatusByDate(date);
        assertThat(status).isEqualTo(AttendanceStatus.ATTENDANCE);
    }

    @DisplayName("크루의 출석 기능을 확인한다.")
    @Test
    void attend_1() {
        Crew crew = new Crew(new ArrayList<>());
        LocalDateTime dateTime = LocalDateTime.of(2024, 12, 13, 10, 0);

        DailyRecord record = crew.attend(dateTime);

        assertThat(crew.findRecordByDate(dateTime.toLocalDate()))
            .isPresent()
            .contains(record);
    }

    @DisplayName("크루의 출석 수정을 확인한다.")
    @Test
    void edit_1() {
        Crew crew = new Crew(new ArrayList<>());
        LocalDateTime oldDateTime = LocalDateTime.of(2024, 12, 13, 10, 0);
        LocalDateTime newDateTime = LocalDateTime.of(2024, 12, 13, 10, 8);

        crew.attend(oldDateTime);
        crew.edit(newDateTime);

        assertThat(crew.findRecordByDate(oldDateTime.toLocalDate()).get().getStatus())
            .isEqualTo(AttendanceStatus.LATENESS);
    }
}