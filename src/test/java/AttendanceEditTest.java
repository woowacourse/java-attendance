import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import domain.AttendanceManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceEditTest {

    @Test
    @DisplayName("출석 상태를 출석에서 지각으로 변경한다.")
    void 출석_상태_변경() {
        AttendanceManager attendanceManager = new AttendanceManager();

        String name = "빙봉";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-15 13:00", formatter);
        LocalDateTime editedDateAndTime = LocalDateTime.parse("2024-12-15 13:06", formatter);
        LocalDate localDate = editedDateAndTime.toLocalDate();

        attendanceManager.createCrew(name, List.of(initialDateAndTime));
        attendanceManager.editCrew(name, editedDateAndTime);

        Records records = attendanceManager.findByName(name);
        TimeAndStatus timeAndStatus = records.findByDate(localDate);

        assertThat(timeAndStatus.getStatus()).isEqualTo("지각");
    }
}
