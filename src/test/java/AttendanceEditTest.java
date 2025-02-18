import domain.CrewRecord;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import net.bytebuddy.asm.Advice.Local;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;

public class AttendanceEditTest {

    @Test
    @DisplayName("출석 정보를 변경한다.")
    void 출석_정보_변경() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        String name = "빙티";
        String dayOfMonth = "3";
        String time = "09:58";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDateTime date = LocalDate.of(2024, 12, Integer.parseInt(dayOfMonth)).atStartOfDay();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date + " " + time, formatter);

        CrewRecord record =attendanceRepository.edit(name, dateTime);
        assertThat(dateTime).isEqualTo(record.getDateTime());

    }

}
