import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.CrewRecord;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import repository.AttendanceRepository;

public class AttendanceCheckTest {

    @Test
    @DisplayName("크루 정보를 출석부에 저장한다.")
    void 크루_정보_저장() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        String name = "빙봉";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        attendanceRepository.attend(name, dateAndTime);
        assertThat(attendanceRepository.findByName(name).size()).isEqualTo(1);
    }

    @Test
    @DisplayName("크루 정보를 출석부에 저장하고 출력한다.")
    void 크루_정보_출력() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        String name = "빙봉";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        attendanceRepository.attend(name, dateAndTime);
        List<CrewRecord> records = attendanceRepository.findByName(name);
        assertThat(dateAndTime).isEqualTo(records.getFirst().getDateTime());
    }

    @Test
    @DisplayName("이미 출석한 경우 수정 기능을 안내한다.")
    void 수정_기능_안내() {
        AttendanceRepository attendanceRepository = new AttendanceRepository();
        String name = "빙봉";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);

        attendanceRepository.attend(name, dateAndTime);

        assertThatThrownBy(() -> {
            attendanceRepository.attend(name, dateAndTime);;
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
