import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import domain.AttendanceManager;
import domain.Records;
import domain.TimeAndStatus;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AttendanceCheckTest {

    AttendanceManager attendanceManager = new AttendanceManager();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Test
    @DisplayName("크루 정보를 출석부에 저장한다.")
    void 크루_정보_저장() {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-13 13:00", formatter);
        LocalDateTime attendDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));
        attendanceManager.attendCrew(name, attendDateAndTime);

        assertThat(attendanceManager.findByName(name).getAttendanceCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("크루 정보를 출석 시간을 저장한다.")
    void 크루_정보_출력() {
        LocalDateTime dateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDate localDate = dateAndTime.toLocalDate();
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of());
        TimeAndStatus timeStatus = attendanceManager.attendCrew(name, dateAndTime);

        Records records = attendanceManager.findByName(name);
        TimeAndStatus expectedTimeStatus = records.findByDate(localDate);

        assertThat(expectedTimeStatus).isEqualTo(timeStatus);
    }

    @Test
    @DisplayName("이미 출석한 경우 수정 기능을 안내한다.")
    void 수정_기능_안내() {
        LocalDateTime initialDateAndTime = LocalDateTime.parse("2024-12-16 13:00", formatter);
        LocalDateTime attendDateAndTime = LocalDateTime.parse("2024-12-16 13:03", formatter);
        String name = "빙봉";

        attendanceManager.createCrew(name, List.of(initialDateAndTime));

        assertThatThrownBy(() -> {
            attendanceManager.attendCrew(name, attendDateAndTime);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
