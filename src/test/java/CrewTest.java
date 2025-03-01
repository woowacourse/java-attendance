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
}
