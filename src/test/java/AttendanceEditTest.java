import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.CrewRecord;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;

public class AttendanceEditTest {

    @Test
    @DisplayName("출석 정보를 변경한다.")
    void 출석_정보_변경() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        attendanceRepository.attend("빙티", LocalDateTime.of(2024, 12, 3,12,58));

        String name = "빙티";
        String dayOfMonth = "3";
        String time = "09:58";

        LocalDateTime expectedDate = LocalDateTime.of(2024, 12, 3,9,58);

        CrewRecord record = attendanceRepository.edit(name, Integer.parseInt(dayOfMonth),time);

        assertThat(expectedDate).isEqualTo(record.getDateTime());
    }
}
